package com.kuda.app.helper;

import java.util.Date;

import com.kuda.app.dto.NewKudaRequest;
import com.kuda.app.enums.KudaType;
import com.kuda.app.model.Kuda;
import com.kuda.app.model.Organization;
import com.kuda.app.model.User;
import com.kuda.app.repository.dto.IterationUserRank;

public class TestInstanceGenerator {

	public static Kuda getKudaSample(Organization organization, User user, NewKudaRequest request, Integer duration) {

		Kuda kuda = new Kuda();

		kuda.setId(Math.round(Math.random() * 1000) + 1);
		kuda.setCreationTime(new Date());
		kuda.setDescription(request.getDescription());
		kuda.setDuration(duration);
		kuda.setFromUser(user);
		kuda.setId(111l);
		kuda.setKudaType(KudaType.fromName(request.getKudaType()));
		kuda.setOrganization(organization);
		kuda.setReadStatus(false);
		kuda.setShowStatus(true);

		return kuda;
	}

	public static IterationUserRank getIterationKudaRanksSample() {

		IterationUserRank instance = new IterationUserRank();

		long feedbacks = Math.round(Math.random() * 100) + 1;
		long thanks = Math.round(Math.random() * 100) + 1;

		instance.setFeedbacks(feedbacks);
		instance.setName("name" + thanks);
		instance.setThanks(thanks);
		instance.setTotal(thanks - feedbacks);
		instance.setUsername("username" + thanks);

		return instance;
	}
}
