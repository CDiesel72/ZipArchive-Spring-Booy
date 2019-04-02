package CDiesel72;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ZipFileRepository extends JpaRepository<ZipFile, Long> {

    //@Query("SELECT MAX(z.id) FROM zipfiles z")
    //@Query("SELECT MAX(id) FROM zipfiles")
    //long findByLastID();
}