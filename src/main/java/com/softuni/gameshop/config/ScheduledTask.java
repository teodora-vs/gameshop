package com.softuni.gameshop.config;
import com.softuni.gameshop.model.ShoppingCart;
import com.softuni.gameshop.model.UserEntity;
import com.softuni.gameshop.repository.ShoppingCartRepository;
import com.softuni.gameshop.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;

@Component
public class ScheduledTask {

    private final Logger logger = LoggerFactory.getLogger(ScheduledTask.class);
    private final UserRepository userRepository;
    private final ShoppingCartRepository shoppingCartRepository;

    public ScheduledTask(UserRepository userRepository,
                         ShoppingCartRepository shoppingCartRepository) {
        this.userRepository = userRepository;
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @Scheduled(cron = "0 1 0 * * *")          // Run every day at 00:01:00
    public void deleteUsersWithNullEmails() {
        List<UserEntity> usersWithNullEmails = userRepository.findByEmailIsNull();
        logger.info("Found {} users with null emails", usersWithNullEmails.size());

        for (UserEntity user : usersWithNullEmails) {
            deleteAssociatedShoppingCarts(user);
            user.setUserRoles(null);
            userRepository.save(user);
            userRepository.delete(user);
            logger.info("Deleted user with id: {} ", user.getId());
        }
        logger.info("Scheduled task completed");
    }

       private void deleteAssociatedShoppingCarts(UserEntity user) {
        Optional<ShoppingCart> shoppingCart = shoppingCartRepository.findByUserId(user.getId());
        if (shoppingCart.isPresent()) {
            shoppingCartRepository.delete(shoppingCart.get());
            logger.info("Deleted shopping cart with id: {}", shoppingCart.get().getId());
        }
    }

}
