package id.ac.ui.cs.mobileprogramming.steffialexandra.PlanMe.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DaoAccess {


    @Query("SELECT * FROM task_model")
    List<TaskModel> getAll();

    @Query("SELECT * FROM task_model WHERE taskid IN (:taskIds)")
    List<TaskModel> loadAllByIds(int[] taskIds);

    @Query("SELECT * FROM task_model WHERE tasktitle LIKE :keyword")
    TaskModel findByName(String keyword);

    @Insert
    long insertTask(TaskModel task);

    @Update
    int updateTask(TaskModel task);

    @Delete
    void reset(List<TaskModel> taskModelList);

    @Query("DELETE FROM task_model WHERE taskid = :itemId")
    int deleteItem(long itemId);
}