package cn.shuaitian.shop.controller;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.shuaitian.shop.controller.annotation.Auth;
import cn.shuaitian.shop.controller.resp.entity.PicUploadResp;
import cn.shuaitian.shop.entity.User;
import cn.shuaitian.shop.entity.UserType;
import cn.shuaitian.shop.utils.SizeUtils;

@Controller
public class FileUploadController {
	private static final String DEFAULT_PIC_DIR = "./images/";

	@Value(value = "#{prop.PICTURE_DIR}")
	private String pictureDir;

	@Value(value = "#{prop.ALLOW_PICTURE_TYPE}")
	private String allowPictureType;

	@Value(value = "#{prop.PICTURE_SIZE}")
	private String pictureSize;

	@RequestMapping(value = "uploadpic", method = RequestMethod.POST)
	@ResponseBody
	@Auth(identity = UserType.SELLER)
	public PicUploadResp uploadpic(MultipartFile localFile, HttpSession session) throws Exception {
		PicUploadResp resp = new PicUploadResp();
		if (StringUtils.isEmpty(pictureDir)) {
			pictureDir = DEFAULT_PIC_DIR;
		}
		File dir = new File(pictureDir);
		if (!dir.exists())
			dir.mkdirs();

		Set<String> allowed = new HashSet<String>();
		if (allowPictureType != null) {
			StringTokenizer st = new StringTokenizer(allowPictureType, "|");
			while (st.hasMoreTokens()) {
				allowed.add(st.nextToken());
			}
		}

		if (localFile == null) {
			resp.setStatus(403);
			resp.setError("文件为空");
			return resp;
		}

		String localName = localFile.getOriginalFilename();
		String suffix = localName.substring(localName.lastIndexOf(".") + 1);
		if (StringUtils.isEmpty(suffix)) {
			resp.setStatus(405);
			resp.setError("不支持的文件后缀名");
			return resp;
		}

		if (!allowed.contains(suffix)) {
			resp.setStatus(406);
			resp.setError("不支持的文件类型");
			return resp;
		}

		long size = localFile.getSize();
		long allowSize = SizeUtils.parse(pictureSize);

		if (size > allowSize) {
			resp.setStatus(404);
			resp.setError("超过文件大小限制（" + pictureSize + "）");
			return resp;
		}

		User user = (User) session.getAttribute("user");
		String fileName = System.currentTimeMillis() + "-" + user.getUsername() + "." + suffix;
		File saveFile = new File(pictureDir + "/" + fileName);
		saveFile.createNewFile();
		localFile.transferTo(saveFile);
		resp.setStatus(200);
		resp.setPicURL("getimage/" + fileName + ".do");
		return resp;
	}

}
