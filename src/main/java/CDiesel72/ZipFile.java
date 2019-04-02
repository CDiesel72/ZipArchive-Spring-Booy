package CDiesel72;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.File;

/**
 * Created by Diesel on 23.03.2019.
 */

@Entity
@Table(name="zipfiles")
@NoArgsConstructor
@Getter
@Setter
public class ZipFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private File file;

    public ZipFile(File file) {
        this.file = file;
    }
}
