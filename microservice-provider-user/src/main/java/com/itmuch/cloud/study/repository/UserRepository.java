package com.itmuch.cloud.study.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itmuch.cloud.study.entity.Member;

@Repository
public interface UserRepository extends JpaRepository<Member, String> {
}
