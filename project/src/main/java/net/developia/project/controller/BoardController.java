package net.developia.project.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;
import net.developia.project.dto.BoardDTO;
import net.developia.project.service.AttachFileService;
import net.developia.project.service.BoardService;
import net.developia.project.dto.AttachFileDTO;


@Log4j
@Controller
@RequestMapping("/main/board/{new_no}/{pg}")
public class BoardController {

	private BoardService boardService;
	
	
	@Value("${pageSize}")
	private long pageSize;
	
	@Value("${blockSize}")
	private long blockSize;
	
	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}
	

	
	@GetMapping("insert")
	public String insertBoard(@PathVariable long new_no, Model model) {
		return "board.insert";
	}	
	
	@PostMapping("insert")
	public String insertBoard(@ModelAttribute BoardDTO boardDTO,
		Model model) {
		
		log.info(boardDTO.toString());
		try {
			boardService.insertBoard(boardDTO);
			return "redirect:../1/";//현재 디렉토리 
		} catch (Exception e) {
			model.addAttribute("msg", "입력 에러");
			model.addAttribute("url", "javascript:history.back();");
			return "result";
		}
	}
	
	@GetMapping("/")
	public String list(@PathVariable long pg, 
		@PathVariable long new_no,
		Model model) throws Exception {
		try {
			long recordCount = boardService.getBoardCount(new_no);
			long pageCount = recordCount / pageSize;
			if (recordCount % pageSize != 0) pageCount++;
			
			long startPage = (pg -1) / blockSize * blockSize + 1;
			long endPage = startPage + blockSize - 1;
			if(endPage > pageCount) endPage = pageCount;
			List<BoardDTO> list = boardService.getBoardListPage(pg,new_no);
			
			model.addAttribute("list", list);
			model.addAttribute("pageCount", pageCount);

			model.addAttribute("pg", pg);
			model.addAttribute("startPage",startPage);
			model.addAttribute("endPage", endPage);
			model.addAttribute("recordCount", recordCount);
			model.addAttribute("pageSize",pageSize);
			return "board.list";
		} catch (Exception e) {
			model.addAttribute("msg", "list 출력 에러");
			model.addAttribute("url", "index");
			return "result";
		}
	}
	
	@Value("${uploadDir}")
	private String uploadFolder;
	
	@GetMapping("/uploadAjax")
	public void uploadAjax() {
		log.info("upload Ajax");
	}
	
	private boolean checkImageType(File file) {
		try {
			String contentType = Files.probeContentType(file.toPath());
			return contentType.startsWith("image");
		}catch(IOException e){
			e.printStackTrace();
		}
		return false;
	}
	
	private String getFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String str = sdf.format(date);
		
		return str.replace("-",File.separator);
	}

	@PostMapping(value = "uploadAjaxAction", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<List<AttachFileDTO>> uploadAjaxPost(MultipartFile[] uploadFile) throws Exception{
		
		List<AttachFileDTO> list = new ArrayList<>();
		String uploadFolder = "/Users/kimbyeounghoon/Desktop/upload/temp";
		String uploadFolderPath = getFolder();
		File uploadPath  = new File(uploadFolder, uploadFolderPath);
		
		log.info("upload path: "+uploadPath);
		
		if(uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}
		
		for (MultipartFile multipartFile : uploadFile) {
			AttachFileDTO attachDTO = new AttachFileDTO();
			String uploadFileName = multipartFile.getOriginalFilename();
			
			log.info("---------------------");
			log.info("Upload File Name:" + multipartFile.getOriginalFilename());
			log.info("Upload File Size:" + multipartFile.getSize());
			
			
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("/") + 1);
			log.info("only file name: "+uploadFileName);
			attachDTO.setFileName(uploadFileName);
			
			UUID uuid = UUID.randomUUID();
			uploadFileName = uuid.toString() + "_" + uploadFileName;
			try {
				File saveFile = new File(uploadPath, uploadFileName);
				multipartFile.transferTo(saveFile);
				
				attachDTO.setUuid(uuid.toString());
				attachDTO.setUploadPath(uploadFolderPath);
				//boardService.insertFile(attachDTO);
				if(checkImageType(saveFile)) {
					attachDTO.setImage(true);
					FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" + uploadFileName));
					Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100, 100);
					thumbnail.close();
				}
				list.add(attachDTO);
			}catch(Exception e){
				log.error(e.getMessage());
			}
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("display")
	@ResponseBody
	public ResponseEntity<byte[]> getFile(String fileName){
		log.info("fileName: "+fileName);
		File file = new File("/Users/kimbyeounghoon/Desktop/upload/temp/"+fileName);
		log.info("file: "+file);
		
		ResponseEntity<byte[]> result = null;
		
		try {
			HttpHeaders header = new HttpHeaders();
			header.add("Content-Type", Files.probeContentType(file.toPath()));
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file),header,HttpStatus.OK);
		}catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	@GetMapping(value = "download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public ResponseEntity<Resource> downloadFile(@RequestHeader("User-Agent") String userAgent, String fileName){
		Resource resource = new FileSystemResource("/Users/kimbyeounghoon/Desktop/upload/temp/"+fileName);
		log.info("download: "+fileName);
		
		if(resource.exists()==false) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		String resourceName = resource.getFilename();
		
		String resourceOriginalName = resourceName.substring(resourceName.indexOf("_")+1);
		log.info("resource: "+resource);
		
		HttpHeaders headers = new HttpHeaders();
		try {
			String downloadName = null;
			if(userAgent.contains("Trident")) {
				log.info("IE browser");
				downloadName = URLEncoder.encode(resourceOriginalName,"UTF-8").replace("\\", " ");
			}else if(userAgent.contains("Edge")) {
				log.info("Edge browser");
				downloadName = URLEncoder.encode(resourceOriginalName,"UTF-8");
			}else {
				log.info("Chrome browser");
				downloadName = new String(resourceOriginalName.getBytes("UTF-8"),"ISO-8859-1");
			}
			log.info("downloadName: "+ downloadName);
			headers.add("Content-Disposition","attachment; filename="+downloadName);
		}catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
	}
	
	@PostMapping("deleteFile")
	@ResponseBody
	public ResponseEntity<String> deleteFile(String fileName, String type){
		log.info("deleteFile: "+fileName);
		File file;
		
		try {
			file = new File("/Users/kimbyeounghoon/Desktop/upload/temp/"+URLDecoder.decode(fileName, "UTF-8"));
			file.delete();
			
			if(type.equals("image")) {
				String largeFileName = file.getAbsolutePath().replace("s_", "");
				log.info("largeFileName: " +largeFileName);
				file = new File(largeFileName);
				file.delete();
			}
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>("deleted",HttpStatus.OK);
	}
	
	
}
