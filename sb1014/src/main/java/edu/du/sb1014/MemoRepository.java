package edu.du.sb1014;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemoRepository extends JpaRepository<Memo, Integer> {
    List<Memo> findAll();
    Memo findById(int id);

    @Query("select new Memo(m.id , m.text) from Memo m order by m.id desc")
    List<Memo> selectById();
}
