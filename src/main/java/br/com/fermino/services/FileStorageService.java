package br.com.fermino.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.util.StringUtils;

import br.com.fermino.config.FileStorageConfig;
import br.com.fermino.exception.FileStorageException;
import br.com.fermino.exception.MyFileNotFoundException;

@Service
public class FileStorageService {

	private final Path fileStorageLocation;
	
	@Autowired
	public FileStorageService(FileStorageConfig fileStorageConfig) {
		
		this.fileStorageLocation=Paths.get(fileStorageConfig.getUploadDir())
								.toAbsolutePath()
								.normalize();
		
		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception e) {
			throw new FileStorageException("Could not create the director to upload files",e);
		}
	}
	
	public String storeFile(MultipartFile file) {
		String fileName= StringUtils.cleanPath(file.getOriginalFilename());
		
		try {
			if(fileName.contains("..")) {
				throw new FileStorageException("Sorry! Invalid filename " + fileName);
			}
			
			Path targetLocation = this.fileStorageLocation.resolve(fileName);
			
			Files.copy(file.getInputStream(),targetLocation, StandardCopyOption.REPLACE_EXISTING);
			
			return fileName;
		} catch (Exception e) {
			throw new FileStorageException("Could not store file " + fileName + ". Please try again.",e);
		}
	}
	
	public Resource loadFileAsResource(String fileName) {
		try {
			Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
			
			Resource resource = new UrlResource(filePath.toUri());
			
			if(resource.exists()) {
				return resource;
			}
			else {
				throw new MyFileNotFoundException("Could not find the file " + fileName);
			}
		} catch (Exception e) {
			throw new MyFileNotFoundException("Could not find the file " + fileName,e);
		}
	}
	
	
}
