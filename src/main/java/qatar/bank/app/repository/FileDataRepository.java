package qatar.bank.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import qatar.bank.app.model.FileData;

@Repository
public interface FileDataRepository extends JpaRepository<FileData, Long> {

    FileData findByFileName(String filename);
}
