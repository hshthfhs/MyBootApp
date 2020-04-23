package com.tuyano.springboot.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tuyano.springboot.MyData;

@Repository   //データアクセスクラスであることを示す
public interface MyDataRepository extends JpaRepository<MyData, Long>{

	//Optional nullかもしれないオブジェクト
	public Optional<MyData> findById(Long name);


}
