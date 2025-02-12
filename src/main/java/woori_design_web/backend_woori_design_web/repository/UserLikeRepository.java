package woori_design_web.backend_woori_design_web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import woori_design_web.backend_woori_design_web.entity.UserLike;

@Repository
public interface UserLikeRepository extends JpaRepository<UserLike, Long> {
}
