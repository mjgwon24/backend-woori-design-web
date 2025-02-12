package woori_design_web.backend_woori_design_web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import woori_design_web.backend_woori_design_web.entity.UserLike;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserLikeRepository extends JpaRepository<UserLike, Long> {
    List<UserLike> findByPostId(Long postId);
    Optional<UserLike> findByUserIdAndPostId(Long userId, Long postId);
}
