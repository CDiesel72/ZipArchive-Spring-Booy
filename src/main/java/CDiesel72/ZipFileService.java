package CDiesel72;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ZipFileService {
    @Autowired
    private ZipFileRepository zipFileRepository;

    @Transactional
    public void addFile(ZipFile zipFile) {
        zipFileRepository.save(zipFile);
    }

    @Transactional
    public void deleteFile(long[] idList) {
        for (long id : idList)
            zipFileRepository.delete(id);
    }

    @Transactional(readOnly = true)
    public List<ZipFile> findAll(Pageable pageable) {
        return zipFileRepository.findAll(pageable).getContent();
    }

    @Transactional(readOnly = true)
    public List<ZipFile> findAll() {
        return zipFileRepository.findAll();
    }

    @Transactional(readOnly = true)
    public ZipFile findOne(long id) {
        return zipFileRepository.findOne(id);
    }

    /*@Transactional(readOnly = true)
    public long lastID() {
        return zipFileRepository.findByLastID();
    }*/
}
