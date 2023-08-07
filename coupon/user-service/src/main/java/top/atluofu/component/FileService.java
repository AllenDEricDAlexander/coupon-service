package top.atluofu.component;

import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName: FileService
 * @description: TODO
 * @author: 有罗敷的马同学
 * @datetime: 2023Year-08Month-07Day-19:17
 * @Version: 1.0
 */
public interface FileService {
    String uploadUserHeadImg( MultipartFile file);
}
