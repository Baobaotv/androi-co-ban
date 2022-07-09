package com.KMA.BookingCare.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.KMA.BookingCare.Dto.WorkTimeDto;
import com.KMA.BookingCare.Entity.WorkTimeEntity;
import com.KMA.BookingCare.Mapper.WorkTimeMapper;
import com.KMA.BookingCare.Repository.WorkTimeRepository;
import com.KMA.BookingCare.Service.WorkTimeService;
@Service
public class WorkTimeServiceImpl implements WorkTimeService{
	
		@Autowired
		private WorkTimeRepository workTimeRepository;

		@Override
		public List<WorkTimeDto> findAll() {
			List<WorkTimeEntity> lstentity = workTimeRepository.findAll();
			List<WorkTimeDto> lstDto = new ArrayList<WorkTimeDto>();
			for(WorkTimeEntity entity : lstentity) {
				WorkTimeDto dto = new WorkTimeDto();
				dto.setId(entity.getId());
				dto.setTime(entity.getTime());
				dto.setName(entity.getName());
				lstDto.add(dto);
			}
			return lstDto;
		}

		@Override
		public List<WorkTimeEntity> findByListId(List<Long> id) {
			// TODO Auto-generated method stub
			return workTimeRepository.findAllById(id);
		}

		@Override
		public List<WorkTimeDto> findByDateAndDoctorId(String date, Long id) {
			List<WorkTimeEntity> lstEntity = workTimeRepository.findByDateAndDoctorId(date, id);
			List<WorkTimeDto> lstDto = new ArrayList<WorkTimeDto>();
			for(WorkTimeEntity entity: lstEntity) {
				WorkTimeDto dto = WorkTimeMapper.convertToDto(entity);
				lstDto.add(dto);
			}
			return lstDto;	
		}

	

}
