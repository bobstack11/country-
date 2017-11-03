
package org.patchca.utils.encoder;

import org.patchca.service.Captcha;
import org.patchca.service.CaptchaService;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.OutputStream;

public class EncoderHelper {

	public static String getChallangeAndWriteImage(CaptchaService service, String format, OutputStream os) throws IOException {
		Captcha captcha = service.getCaptcha();
		ImageIO.write(captcha.getImage(), format, os); 
		return captcha.getChallenge();
	}
	
}

