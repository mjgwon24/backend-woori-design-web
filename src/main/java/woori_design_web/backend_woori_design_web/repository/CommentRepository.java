package woori_design_web.backend_woori_design_web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import woori_design_web.backend_woori_design_web.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
