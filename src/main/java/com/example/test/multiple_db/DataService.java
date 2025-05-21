package com.example.test.multiple_db;


import com.example.test.multiple_db.h2.entity.AuditLog;
import com.example.test.multiple_db.h2.repo.AuditLogRepository;
import com.example.test.multiple_db.mysql.entity.User;
import com.example.test.multiple_db.mysql.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataService {

    //com.example.test.multiple_db.h2.repo
    @Autowired
    AuditLogRepository auditLogRepository;

    @Autowired
    UserRepository userRepository ;

    public  void setUserANDLogAudit(){

        userRepository.save(new User(2L,"Gopal","gopal123","EC"));
        auditLogRepository.save(new AuditLog(1L, "Inserted Alice"));
    }
}
