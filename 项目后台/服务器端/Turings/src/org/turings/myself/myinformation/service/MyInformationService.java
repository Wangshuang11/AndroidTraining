package org.turings.myself.myinformation.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.turings.myself.entity.Myself;
import org.turings.myself.myinformation.dao.MyInformationMapper;

import com.google.gson.Gson;
@Service
@Transactional(readOnly=true)

public class MyInformationService {

	@Resource
	private MyInformationMapper myInformationMapper;
	
	public Myself getMyInformation(String uid) {
		return this.myInformationMapper.getMyInformation(uid);
	}

	public String toJsonArray(Myself myself) {
		System.out.println("id"+myself.getId());
		Gson gson = new Gson();
		String json = gson.toJson(myself);
		return json;
	}

}
