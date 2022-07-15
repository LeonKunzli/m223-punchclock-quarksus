package ch.zli.m223.punchclock.util;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.transaction.Transactional;

import ch.zli.m223.punchclock.domain.Category;
import ch.zli.m223.punchclock.domain.Role;
import ch.zli.m223.punchclock.domain.User;
import ch.zli.m223.punchclock.service.CategoryService;
import ch.zli.m223.punchclock.service.RoleService;
import ch.zli.m223.punchclock.service.UserService;
import io.quarkus.runtime.StartupEvent;


@Singleton
public class Startup {

    @Inject
    RoleService roleService;

    @Inject 
    UserService userService;

    @Inject
    CategoryService categoryService;

    @Transactional
    public void init(@Observes StartupEvent evt) {
        //Create roles
        Role admin = new Role();
        Role worker = new Role();
        admin.setRole("admin");
        worker.setRole("user");
        roleService.createRole(admin);
        roleService.createRole(worker);
        //Create Admin User
        User user = new User();
        user.setUsername("Elia");
        user.setPassword("1234");
        user.getRoles().add(admin);
        userService.createUser(user);
        //Create Default Category
        Category defaultCat = new Category();
        defaultCat.setName("Default Category");
        categoryService.createCategory(defaultCat);
        
        System.out.println("Initialized!");
    }
}