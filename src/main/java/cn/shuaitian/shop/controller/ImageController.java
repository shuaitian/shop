package cn.shuaitian.shop.controller;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ImageController {
	@Value(value = "#{prop.PICTURE_DIR}")
	private String pictureDir;
	
	@RequestMapping(value="getimage/{filename}",method=RequestMethod.GET)
	public void getimage(HttpServletResponse resp,@PathVariable("filename") String filename) {
		if(StringUtils.isEmpty(filename))
			return ;
		if(filename.endsWith(".")) {
			try {
				resp.getWriter().println("不支持的图片类型");
			} catch (IOException e) {
				//TODO 记录日志
				e.printStackTrace();
			}
			return ;
		}
		String suffix = filename.substring(filename.lastIndexOf(".") + 1); 
		resp.setContentType("image/" + suffix);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(pictureDir + "/" + filename);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[8192];
			int len;
			while((len = fis.read(buffer)) != -1) {
				baos.write(buffer, 0, len);
			}
			resp.getOutputStream().write(baos.toByteArray());
		} catch (FileNotFoundException e) {
			//TODO 记录日志
			e.printStackTrace();
		} catch (IOException e) {
			//TODO 记录日志
			e.printStackTrace();
		}finally {
			if(fis!=null) {
				try {
					fis.close();
				} catch (IOException e) {
					//TODO 记录日志
					e.printStackTrace();
				}
			}
		}
		
	}
}
