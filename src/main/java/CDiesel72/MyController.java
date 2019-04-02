package CDiesel72;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Controller
@RequestMapping("/")
public class MyController {

    @Autowired
    private ZipFileService zipFileService;
    //static final int ITEMS_PER_PAGE = 6;
    private final String PATH_FILE = "D:\\files\\";

    @RequestMapping("/")
    public String onIndex(Model model) {
        //model.addAttribute("files",
        //        zipFileService.findAll(new PageRequest(0, ITEMS_PER_PAGE, Sort.Direction.DESC, "id")));
        model.addAttribute("files", zipFileService.findAll());
        return "index";
    }

    @RequestMapping(value = "/add_file", method = RequestMethod.POST)
    public String onAddFile(Model model, @RequestParam("fileD") MultipartFile[] fileD) {
        if (fileD.length <= 0 || fileD.length > 5) {
            throw new FileDownErrorException();
        }

        //long nextId = zipFileService.lastID() + 1;
        int id = new Random().nextInt(100000);
        File zip = new File(PATH_FILE, "Archive_" + id + ".zip");

        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zip))) {
            for (MultipartFile mf : fileD) {
                zos.putNextEntry(new ZipEntry(mf.getOriginalFilename()));
                zos.write(mf.getBytes());
                zos.closeEntry();
            }

            zipFileService.addFile(new ZipFile(zip));

            return onIndex(model);
        } catch (IOException e) {
            throw new FileDownErrorException();
        }
    }

    @RequestMapping("/file/{file_id}")
    public ResponseEntity<byte[]> onFile(@PathVariable("file_id") long id) {
        return fileById(id);
    }

    @RequestMapping("/delete/{file_id}")
    public String onDelete(Model model, @PathVariable("file_id") String strIds) {

        try {
            String[] arStrId = strIds.split("[,]");
            long[] arLongId = new long[arStrId.length];
            int i = 0;
            for (String s : arStrId) {
                long id = Long.valueOf(s);
                arLongId[i++] = id;
                zipFileService.findOne(id).getFile().delete();
            }

            zipFileService.deleteFile(arLongId);

            return onIndex(model);
        } catch (Exception e) {
            throw new FileDownErrorException();
        }

    }

    private ResponseEntity<byte[]> fileById(long id) {
        File file = zipFileService.findOne(id).getFile();

        try (InputStream is = new FileInputStream(file)) {
            byte[] bytes = new byte[(int) file.length()];
            is.read(bytes);
            if (bytes == null)
                throw new FileDownNotFoundException();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            return new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);

        } catch (IOException e) {
            throw new FileDownNotFoundException();
        }
    }
}
