package hr.project.restController;

import hr.project.ActiveUserStore;
import hr.project.exceptionHandling.Error;
import hr.project.exceptionHandling.ObjectNotFound;
import hr.project.model.LoggedUser;
import hr.project.model.User;
import hr.project.repository.UserRepository;
import hr.project.util.WebAgentSessionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.ServletContext;
import java.io.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/user")
public class UserRestController {

    private final UserRepository userRepository;

    @Autowired
    UserRestController(UserRepository userRepository) {this.userRepository = userRepository;}

    @Autowired
    ServletContext context;

    @Autowired
    ActiveUserStore activeUserStore;

    @Autowired
    WebAgentSessionRegistry webAgentSessionRegistry;

    @ExceptionHandler(ObjectNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error objectNotFound(ObjectNotFound e) {
        Integer id = e.getObjectId();
        return new Error(404, "Object [" + id + "] not found");
    }

    @RequestMapping(value="/activeUsers", method=RequestMethod.GET)
    public ResponseEntity<ConcurrentHashMap<String, String>> getAllUsers() {
        ConcurrentHashMap<String, String> map = webAgentSessionRegistry.getConcurrentMap();
        if (map.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(map);
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<List<User>> findAll() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(users);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<User> findById(@PathVariable Integer id) {
        User user = userRepository.findById(id);
        if (user == null) { throw new ObjectNotFound(id); }
        return new ResponseEntity<>(user, null, HttpStatus.OK);
    }

    @RequestMapping(value="/login", method=RequestMethod.POST, consumes="application/json")
    public ResponseEntity<User> login(@RequestBody User user, UriComponentsBuilder ucb) {
        System.out.println(user);
        User userInDB = userRepository.findByEmail(user.getEmail());
        System.out.println("userInDB " + userInDB);
        if (userInDB == null) {
            return new ResponseEntity<>(user, null, HttpStatus.UNAUTHORIZED);
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (encoder.matches(user.getPassword(), userInDB.getPassword())) {
            LoggedUser loggedUser = new LoggedUser(activeUserStore);
            loggedUser.setLoggedIn(userInDB);
            return new ResponseEntity<>(userInDB, null, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(user, null, HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value="/logout", method=RequestMethod.POST, consumes="application/json")
    public ResponseEntity<User> logout(@RequestBody User user, UriComponentsBuilder ucb) {
        User userInDB = userRepository.findByEmail(user.getEmail());
        if (userInDB != null) {
            LoggedUser loggedUser = new LoggedUser(activeUserStore);
            loggedUser.setLoggedOut(user);
            return new ResponseEntity<>(userInDB, null, HttpStatus.OK);
        }
        return new ResponseEntity<>(user, null, HttpStatus.NOT_FOUND);
    }

    @RequestMapping(method=RequestMethod.POST, consumes="application/json")
    public ResponseEntity<User> save(@RequestBody User user, UriComponentsBuilder ucb) {
        user.setPassword(new BCryptPasswordEncoder(15).encode(user.getPassword()));

        try {
            user = userRepository.save(user);
            return new ResponseEntity<>(user, null, HttpStatus.CREATED);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(user);
        }
    }

    /**
     * Upload user photo
     */
    @RequestMapping(value = "/{id}/uploadImage", method = RequestMethod.POST)
    public ResponseEntity<User> uploadFileHandler(@PathVariable Integer id,
                                    @RequestParam("image") MultipartFile file) {

        String absolutePath = context.getRealPath("resources/");

        User user = null;

        if (!(userRepository.exists(id))){
            return ResponseEntity.notFound().build();
        } else {
            user = userRepository.findById(id);
        }

        String fileName = file.getOriginalFilename();

        if (!file.isEmpty()) {
            try {

                // This buffer will store the data read from 'file'
                byte[] buffer = new byte[1000];

                String path = absolutePath + File.separator + id + File.separator + fileName;

                // Creating the directory to store file
                File dir = new File(absolutePath + File.separator + id);
                if (!dir.exists())
                    dir.mkdirs();

                File outputFile = new File(path);

                FileInputStream reader = null;
                FileOutputStream writer = null;
                try {
                    outputFile.createNewFile();

                    // Create the input stream of the uploaded file to read data from it.
                    reader = (FileInputStream) file.getInputStream();

                    // Create writer for 'outputFile' to write data read from 'file'
                    writer = new FileOutputStream(outputFile);

                    // Iteratively read data from 'file' and write to 'outputFile'
                    int bytesRead = 0;
                    while ((bytesRead = reader.read(buffer)) != -1) {
                        writer.write(buffer);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        reader.close();
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                user.setId(id);
                user.setImage(fileName);
                userRepository.save(user);

                return new ResponseEntity<>(user, null, HttpStatus.OK);
            } catch (Exception e) {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(path="/{id}",method=RequestMethod.PUT)
    public ResponseEntity<User> update(@RequestBody User user,@PathVariable Integer id)  {

        if (!(userRepository.exists(id))){
            return ResponseEntity.notFound().build();
        }
        user.setId(id);
        // temporary fix - problem : (since we don't send the password with this request, it puts the password to NULL in the DB)
        User userInDB = userRepository.findByEmail(user.getEmail());
        user.setPassword(userInDB.getPassword());
        userRepository.save(user);
        return new ResponseEntity<>(user, null, HttpStatus.OK);
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value="/{id}", method= RequestMethod.DELETE)
    public ResponseEntity<String> deleteById(@PathVariable Integer id) {
        try {
            userRepository.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Item successfully deleted!");
        }
        catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error deleting the item:" + ex.toString());
        }
    }
}
