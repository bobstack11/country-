package com.egouos.weixin.config;

import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import net.sf.json.JSONObject;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.egouos.util.MD5Util;
import com.egouos.util.StringUtil;

public class WxPayData {

	//采用排序的SortedMap的好处是方便对数据包进行签名，不用再签名之前再做一次排序
	private SortedMap<String, Object> m_values = new TreeMap<String, Object>();
	private String apiKey = null;
	
	public WxPayData(){

	}
	
	public WxPayData(String apiKey){
		this.apiKey = apiKey;
	}
	
	/**
	 * 设置某个字段的值
	 * @param key 字段名
	 * @param value 字段值
	 */
	public void setValue(String key, Object value){
		m_values.put(key, value);
	}
	
	/**
	 * 根据字段名获取某个字段的值
	 * @param key
	 * @return
	 */
	public Object getValue(String key){
		return m_values.get(key);
	}
	
	/**
	 * 判断某个字段是否已设置
	 * @param key
	 * @return 若字段key已被设置，则返回true，否则返回false
	 */
	public boolean isSet(String key){
		return (getValue(key)!=null);
	}
	
	/**
	 * 将SortedMap转成xml
	 * @return 经转换得到的xml串
	 * @throws WxPayException
	 */
	public String toXml() throws WxPayException{
		//数据为空时不能转化为xml格式
		if(m_values.size()==0){
			throw new WxPayException("WxPayData数据为空!");
		}
		String xml = "<xml>";
		for(final Iterator<String> i = m_values.keySet().iterator(); i.hasNext();){
			
			final String key = i.next();
			if(key==null){
				throw new WxPayException("WxPayData内部含有值为null的字段!");
			}
			if(m_values.get(key) instanceof Integer){
				xml += "<" + key + ">" + m_values.get(key) + "</" + key + ">";
			}else if (m_values.get(key) instanceof String){
				xml += "<" + key + ">" + "<![CDATA[" + m_values.get(key) + "]]></" + key + ">";
			}else//除了String和Integer类型不能含有其他数据类型
			{
				throw new WxPayException("WxPayData字段数据类型错误!");
			}
			//sbuf.append(k==0?"":'&').append(name).append('=').append(params.get(name));
		}
		xml += "</xml>";
		return xml;
	}
	
	
	public SortedMap<String, Object> fromXml(String xml) throws Exception{
		if (StringUtil.isEmpty(xml))
        {
            throw new WxPayException("将空的xml串转换为WxPayData不合法!");
        }

		final Document xmlDoc = DocumentHelper.parseText(xml);
		//获取到根节点<xml>
		final Element xmlNode = xmlDoc.getRootElement();
		for(Object xn : xmlNode.elements()){
			Element xe = (Element)xn;
			//获取xml的键值对到WxPayData内部的数据中
			m_values.put(xe.getName(), xe.getText());
		}
		
		try
		{
			//2015-06-29 错误是没有签名
			if(!"SUCCESS".equals(m_values.get("return_code")))
			{
				return m_values;
			}
			checkSign();//验证签名,不通过会抛异常
		}
		catch(WxPayException ex)
		{
			throw new WxPayException(ex.getMessage());
		}

		return m_values;
	}
	
	/**
	 * @SortedMap格式转化成url参数格式
	 * @ return url格式串, 该串不包含sign字段值
	*/
	public String toUrl() throws WxPayException {
		StringBuilder sbuf = new StringBuilder();
		int k = 0;
		for(final Iterator<String> i = m_values.keySet().iterator(); i.hasNext();++k){
			final String name = i.next();
			if(m_values.get(name)==null){
				throw new WxPayException("WxPayData内部含有值为null的字段!");
			}
			if(!"sign".equals(name) && !"".equals(m_values.get(name))){
				sbuf.append(k==0?"":'&').append(name).append("=").append(m_values.get(name));
			}
		}
		return sbuf.toString();
	}

	/**
	 * @Dictionary格式化成Json
	 * @return json串数据
	 */
	public String toJson() {
		String jsonStr = JSONObject.fromObject(m_values).toString();
		return jsonStr;
	}

	/**
	 * @values格式化成能在Web页面上显示的结果（因为web页面上不能直接输出xml格式的字符串）
	 */
	public String toPrintStr() throws WxPayException {
		String str = "";
		for(final Iterator<String> i = m_values.keySet().iterator(); i.hasNext();){
			final String name = i.next();
			if(m_values.get(name)==null){
				throw new WxPayException("WxPayData内部含有值为null的字段!");
			}
			str += name+"="+m_values.get(name).toString()+"<br>";
		}
		return str;
	}

	/**
     * @生成签名，详见签名生成算法
     * @return 签名, sign字段不参加签名
     */
     public String makeSign() throws WxPayException {
         //转url格式
         String str = toUrl();
         //在string后加入API KEY
         str += "&key=" + apiKey;
         return (MD5Util.encode(str, "UTF-8").toUpperCase());
     }
     
	/**
     * 检测签名是否正确
     * 正确返回true，错误抛异常
     */
	public boolean checkSign() throws WxPayException {
		//如果没有设置签名，则跳过检测
        if (!isSet("sign"))
        {
           throw new WxPayException("WxPayData签名存在但不合法!");
        }
        //如果设置了签名但是签名为空，则抛异常
        else if(getValue("sign") == null || "".equals(getValue("sign")))
        {
            throw new WxPayException("WxPayData签名存在但不合法!");
        }

        //获取接收到的签名
        String return_sign = getValue("sign").toString();

        //在本地计算新的签名
        String cal_sign = makeSign();
        //System.out.println("cal_sign="+cal_sign);
        //System.out.println("return_sign="+return_sign);
        if (cal_sign.equals(return_sign))
        {
            return true;
        }

        throw new WxPayException("WxPayData签名验证错误!");
	}
	
	public static void main(String[] args) throws Exception
	{
		String xml = "<xml>";
		xml += "<appid><![CDATA[wxee16062a48018778]]></appid>";
		xml += "<attach><![CDATA[test]]></attach>";
		xml += "<bank_type><![CDATA[CFT]]></bank_type>";
		xml += "<cash_fee><![CDATA[1]]></cash_fee>";
		xml += "<fee_type><![CDATA[CNY]]></fee_type>";
		xml += "<is_subscribe><![CDATA[Y]]></is_subscribe>";
		xml += "<mch_id><![CDATA[1267643801]]></mch_id>";
		xml += "<nonce_str><![CDATA[b2eb7349035754953b57a32e2841bda5]]></nonce_str>";
		xml += "<openid><![CDATA[oTV2ZwWxXnq7_L675zq9GZemGMsg]]></openid>";
		xml += "<out_trade_no><![CDATA[20150915230628]]></out_trade_no>";
		xml += "<result_code><![CDATA[SUCCESS]]></result_code>";
		xml += "<return_code><![CDATA[SUCCESS]]></return_code>";
		xml += "<sign><![CDATA[BDBF597D31C9C4FB808E4AADE69EB213]]></sign>";
		xml += "<time_end><![CDATA[20150915230617]]></time_end>";
		xml += "<total_fee>1</total_fee>";
		xml += "<trade_type><![CDATA[JSAPI]]></trade_type>";
		xml += "<transaction_id><![CDATA[1003870917201509150891852053]]></transaction_id>";
		xml += "</xml>";
		
		WxPayData data = new WxPayData("3j9375x94osfd32342637fhgfwae07lc");
		try {
			data.fromXml(xml);
		} catch (WxPayException ex) {
			System.out.println(ex.getMessage());
		}
	}
}
