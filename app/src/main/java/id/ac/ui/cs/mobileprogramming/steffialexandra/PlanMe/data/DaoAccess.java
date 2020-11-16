package id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DaoAccess {

    //for task model
    @Query("SELECT * FROM task_model")
    List<TaskModel> getAllTasks();

    @Query("SELECT * FROM task_model WHERE creator IN (:name)")
    List<TaskModel> loadAllByUsername(String name);

    @Query("SELECT * FROM task_model WHERE taskid LIKE :id")
    TaskModel findById(int id);

    @Insert
    long insertTask(TaskModel task);

    @Update
    int updateTask(TaskModel task);

    @Delete
    void reset(List<TaskModel> taskModelList);

    @Query("DELETE FROM task_model WHERE taskid = :itemId")
    int deleteItem(long itemId);

    @Query("DELETE FROM task_model")
    int deleteAll();

    //for user model
    @Insert
    long addUser(UserModel userModel);

    @Query("SELECT * FROM user_model")
    List<UserModel> getAllUsers();

    @Query("SELECT * FROM user_model WHERE username LIKE :uname")
    UserModel findByUsername(String uname);

    @Query("SELECT * FROM user_model WHERE username LIKE :uname AND password LIKE :password")
    UserModel getUser(String uname, String password);

    //for history model
    @Query("SELECT * FROM history_model WHERE userModel LIKE:user")
    List<HistoryModel> getAllUserHistoryByUsername(String user);

    @Query("SELECT * FROM user_model WHERE username LIKE:uname")
    boolean getUserByUsername(String uname);
}