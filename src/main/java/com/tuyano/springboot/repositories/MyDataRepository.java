package com.tuyano.springboot.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tuyano.springboot.MyData;

@Repository   //データアクセスクラスであることを示す
public interface MyDataRepository extends JpaRepository<MyData, Long>{

	//Optional nullかもしれないオブジェクト()
	public Optional<MyData> findById(Long name);


/* いろんなList取得
	public List<MyData> findByNameLike(String name);      //nameのあいまい検索(引数には"%" + str など)
	public List<MyData> findByIdNotNullOrderByIdDesc();   //IDがnullでないレコードを降順
	public List<MyData> findByAgeGreaterThan(Integer age);//Integer ageよりも大きい値のレコードを取得
	public List<MyData> findByAgeBetween(Integer age1, Integer age2); //age1からage2までのレコード
*/


}
