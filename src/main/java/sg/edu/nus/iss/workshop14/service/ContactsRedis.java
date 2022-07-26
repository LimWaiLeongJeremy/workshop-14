package sg.edu.nus.iss.workshop14.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.workshop14.model.Contact;

@Service
public class ContactsRedis implements ContactsRepo {
    private static final Logger logger = LoggerFactory.getLogger(ContactsRedis.class);

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Override
    public void save(final Contact ctc) {
        redisTemplate.opsForList().leftPush("name", ctc.getName());
        redisTemplate.opsForList().leftPush("email", ctc.getEmail());
        redisTemplate.opsForList().leftPush("phonenumber", ctc.getPhoneNumber());
        
        // store as object 
        // redisTemplate.opsForValue().set(ctc.getId(), ctc);
    }

    @Override
    public Contact findById(final String contactId) {
        String name = (String) redisTemplate.opsForList().index("name", 1L);
        String email = (String) redisTemplate.opsForList().index("email", 1L);
        Integer phonenum = (Integer) redisTemplate.opsForList().index("phonenumber", 1L);
        logger.info("contact name > ", name);
        logger.info("contact email > ", email);
        logger.info("contact phonenumber > ", phonenum);

        Contact ct = new Contact();
        ct.setName(name);
        ct.setEmail(email);
        ct.setPhoneNumber(phonenum);
        ct.setId(contactId);
        
        // retriving as objected 
        // Contact result = (Contact) redisTemplate.opsForValue().get(contactId);
        // logger.info(">>> " + result.getEmail());
        // return result;
        return ct;
    }
}
