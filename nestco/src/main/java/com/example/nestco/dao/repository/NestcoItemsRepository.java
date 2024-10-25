package com.example.nestco.dao.repository;

import com.example.nestco.models.entity.Category;
import com.example.nestco.models.entity.Member;
import com.example.nestco.models.entity.NestcoItems;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NestcoItemsRepository extends JpaRepository<NestcoItems, Long> {


    // 최신 등록 상품 조회(페이징 처리)
    Page<NestcoItems> findAllByOrderByCreateDateDesc(Pageable pageable);

    // 거래 완료 상태가 아닌 아이템만 조회
    List<NestcoItems> findByStatusFalse();

    //  상품명으로 상품 조회

    Page<NestcoItems> findByTitleContaining(String title, Pageable pageable);

    // 회원이 업로드한 상품을 조회하는 메소드
    List<NestcoItems> findByUploader(Member uploader);

    // 여러 카테고리 ID 리스트로 상품 조회
    @Query("SELECT i FROM NestcoItems i WHERE i.category.id IN :categoryIds")
    Page<NestcoItems> findByCategoryIds(@Param("categoryIds") List<Long> categoryIds, Pageable pageable);

    // 카테고리 ID 리스트와 검색어로 상품 조회
    @Query("SELECT i FROM NestcoItems i WHERE i.category.id IN :categoryIds AND i.title LIKE %:title%")
    Page<NestcoItems> findByCategoryIdsAndTitle(@Param("categoryIds") List<Long> categoryIds, @Param("title") String title, Pageable pageable);

    // 주소로 필터링하여 상품 조회
    @Query("SELECT i FROM NestcoItems i WHERE i.uploader.address LIKE %:address%")
    List<NestcoItems> findByUploaderAddress(@Param("address") String address);

    // 카테고리와 주소로 필터링
    @Query("SELECT i FROM NestcoItems i WHERE i.category.id = :categoryId AND i.uploader.address LIKE %:address%")
    List<NestcoItems> findByCategoryAndUploaderAddress(@Param("categoryId") Long categoryId, @Param("address") String address);

    // 거래 상태, 등록일 순, 지역으로 필터링하여 상품 조회
    @Query("SELECT i FROM NestcoItems i WHERE " +
            "(:status IS NULL OR i.status = :status) AND " +
            "(:address IS NULL OR i.uploader.address LIKE %:address%) " +
            "ORDER BY i.createDate DESC")
    Page<NestcoItems> findByStatusAndUploaderAddress(@Param("status") Boolean status,
                                                     @Param("address") String address,
                                                     Pageable pageable);

    @Query(value = "SELECT n FROM NestcoItems AS n WHERE n.uploader.userId LIKE %:searchKey%")
    Page<NestcoItems> findAllByQuery(Pageable pageable, @Param("searchKey") String searchKey);


}
