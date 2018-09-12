package ru.javawebinar.topjava.web.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.util.exception.UserDataException;

import java.util.List;
import java.util.Locale;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

public abstract class AbstractUserController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService service;

    @Autowired
    MessageSource messageSource;

    public List<User> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    public User get(int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    public User create(User user, Locale locale) {
        log.info("create {}", user);
        checkNew(user);
        try {
            return service.create(user);
        } catch (RuntimeException e) {
            checkForMailDuplicate(e, locale);
        }
        return null;
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    public void update(User user, int id, Locale locale) {
        log.info("update {} with id={}", user, id);

        assureIdConsistent(user, id);
        try {
            service.update(user);
        } catch (RuntimeException e) {
            checkForMailDuplicate(e, locale);
        }
    }

    public void update(UserTo userTo, int id, Locale locale) {
        log.info("update {} with id={}", userTo, id);
        assureIdConsistent(userTo, id);
        try {
            service.update(userTo);
        } catch (RuntimeException e) {
            checkForMailDuplicate(e, locale);
        }
    }

    public User getByMail(String email) {
        log.info("getByEmail {}", email);
        return service.getByEmail(email);
    }

    public void enable(int id, boolean enabled) {
        log.info(enabled ? "enable {}" : "disable {}", id);
        service.enable(id, enabled);
    }


    private void checkForMailDuplicate(RuntimeException e, Locale locale) {

        if (locale == null) {
            locale = Locale.ENGLISH;
        }
        String message = messageSource.getMessage("user.duplicatedEmail", null, locale);
        if (ValidationUtil.getRootCause(e).getMessage().contains("users_unique_email_idx")) {
            throw new UserDataException(message);
        }
        throw e;
    }

}