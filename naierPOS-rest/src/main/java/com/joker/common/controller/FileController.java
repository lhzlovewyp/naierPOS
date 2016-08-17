/**
 * 
 */
package com.joker.common.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.joker.core.annotation.NotNull;
import com.joker.core.cache.CacheFactory;
import com.joker.core.constant.ResponseState;
import com.joker.core.controller.AbstractController;
import com.joker.core.dto.ReturnBody;
import com.joker.core.util.Configer;
import com.joker.core.util.FileUtil;

/**
 * @author lvhaizhen
 *
 */
@RestController
public class FileController extends AbstractController{

	@RequestMapping(value = { "/file/getImg" }, method = RequestMethod.GET)
	public void getImg(
			HttpServletRequest request, HttpServletResponse response) {
		//首先获取图片的唯一id
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		String ext = request.getParameter("ext");
		String prefix=Configer.get("imgPath");
		
		String fileName=prefix+type+"/"+id+"."+ext;
		
		File file=new File(fileName);
		
		response.setHeader("Content-Type","image/jped");
		FileInputStream stream = null;
		OutputStream toClient = null;
		try {
			stream = new FileInputStream(file);
			//得到文件大小   
	        int i=stream.available(); 
	        byte data[]=new byte[i]; 
	        //读数据   
	        stream.read(data);
			
			 //得到向客户端输出二进制数据的对象
	        toClient=response.getOutputStream();  
	        //输出数据   
	        toClient.write(data);  
	        
	        toClient.flush();  
	        toClient.close();   
	        stream.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			IOUtils.closeQuietly(stream);
			IOUtils.closeQuietly(toClient);
		}
		
		
	}
	
	
	@RequestMapping(value = { "/file/uploadImg" }, method = RequestMethod.POST)
	@NotNull(value = "token")
	@ResponseBody
	public ReturnBody uploadFile(@RequestParam(value = "file_upload", required = false) MultipartFile uploadFile,HttpServletRequest request, HttpServletResponse response) {
		ReturnBody rbody = new ReturnBody();
		 //获取并解析文件类型和支持最大值  
        String fileType = "jpg,gif,bmp,jpeg,png";  
        String type=request.getParameter("type");

		String token = request.getParameter("token");
		Object user = CacheFactory.getCache().get(token);
		if (user != null) {
			//临时目录名  
		    String tempPath = Configer.get("imgTempPath")+type+"/";  
		    //真实目录名  
		    String filePath = Configer.get("imgPath")+type+"/"; 
		    
		    FileUtil.createFolder(filePath);  
		    FileUtil.createFolder(tempPath); 
			try {
			 //文件名  
             String fileName = uploadFile.getOriginalFilename();  
             //检查文件后缀格式  
             String fileEnd = fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();  
             if(StringUtils.isNotBlank(fileType)){  
                    boolean isRealType = false;  
                    String[] arrType = fileType.split(",");  
                    for (String str : arrType) {  
                        if(fileEnd.equals(str.toLowerCase())){  
                            isRealType = true;  
                            break;  
                        }  
                    }  
                    if(!isRealType){  
                        //提示错误信息:文件格式不正确  
                       // super.printJsMsgBack(response, "文件格式不正确!");  
                    	rbody.setMsg("文件格式不正确");
                    	rbody.setStatus(ResponseState.FAILED);
                       return null;  
                    }  
                } 
             	//创建文件唯一名称  
                String uuid = UUID.randomUUID().toString();  
                //真实上传路径  
                StringBuffer sbRealPath = new StringBuffer();  
                sbRealPath.append(filePath).append(uuid).append(".").append(fileEnd);  
                //写入文件  
                File file = new File(sbRealPath.toString());  
                uploadFile.transferTo(file);
                
                rbody.setData(URLEncoder.encode("/rest/file/getImg?type="+type+"&id="+uuid+"&ext="+fileEnd,"utf-8"));
            	rbody.setStatus(ResponseState.SUCCESS);
			} catch (Exception e) {
				e.printStackTrace();
				rbody.setMsg("文件上传出现系统问题,请稍后重试");
	        	rbody.setStatus(ResponseState.FAILED);
			}  
		} else {
			rbody.setStatus(ResponseState.ERROR);
			rbody.setMsg("请登录！");
		}
		// 数据返回时永远返回true.
		return rbody;
	}
	
}
