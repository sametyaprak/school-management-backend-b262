package com.project.schoolmanagment.service.helper;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class PageableHelper {
  
  public Pageable getPageable(int page,int size, String sort, String type){
    Pageable pageable = PageRequest.of(page,size, Sort.by(sort).ascending());
    if(type.equals("desc")){
      pageable = PageRequest.of(page,size, Sort.by(sort).descending());
    }
    return pageable;
  }

}
