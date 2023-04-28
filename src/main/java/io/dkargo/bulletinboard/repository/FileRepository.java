package io.dkargo.bulletinboard.repository;

import io.dkargo.bulletinboard.entity.PostFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<PostFile, Long> {
}
