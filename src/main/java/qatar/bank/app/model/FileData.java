package qatar.bank.app.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "file_data")
public class FileData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "file_size")
    private Long fileSize;
    @Column(name = "location")
    private String location;
    @Column(name = "post_id")
    private Long postId;
}
