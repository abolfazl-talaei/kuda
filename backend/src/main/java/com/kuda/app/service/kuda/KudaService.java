package com.kuda.app.service.kuda;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.kuda.app.dto.NewKudaRequest;
import com.kuda.app.dto.response.KudaInfo;
import com.kuda.app.exception.KudaException;
import com.kuda.app.model.Kuda;
import com.kuda.app.repository.dto.IterationUserRank;
import com.kuda.app.repository.dto.KudaDefaultFeedback;

public interface KudaService {

	List<KudaInfo> getKudas(String username);

	List<Kuda> getCurrentDurationKudas(String user) throws KudaException;

	Optional<Kuda> getKudaById(long id);

	void deleteKuda(long id);

	KudaInfo createKuda(String username, NewKudaRequest request) throws KudaException;

	List<IterationUserRank> getReadStatistics(String user);

	void changeShowStatus(String username, Boolean status) throws KudaException;

	List<IterationUserRank> getIterationKudaRanks(String username, Integer duration);

	List<KudaDefaultFeedback> getFeedbackTypes(String username) throws KudaException;

	Map<String, Object> getKudaDurationMap(String username) throws KudaException;

	KudaInfo chnageKudaReadStatus(Long id) throws KudaException;
}
