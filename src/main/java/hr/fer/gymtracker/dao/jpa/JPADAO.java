package hr.fer.gymtracker.dao.jpa;

import hr.fer.gymtracker.dao.DAO;
import hr.fer.gymtracker.models.*;
import hr.fer.gymtracker.models.Set;
import hr.fer.gymtracker.util.DatetimeUtil;
import jakarta.persistence.NoResultException;

import java.util.*;

/**
 * Class implements DAO interface and provides methods for saving, deleting and getting data from database.
 * It uses JPA for communication with database.
 */
public class JPADAO implements DAO {

    private static final HashMap<Integer, SetType> setTypes = new HashMap<>();

    @Override
    public void save(Object o) {
        JPAEMProvider.getEntityManager().persist(o);
    }

    @Override
    public void deleteTrainingSession(TrainingSession trainingSession) {
        deleteSets(trainingSession.getSets());
        JPAEMProvider.getEntityManager().createQuery("DELETE FROM TrainingSession t WHERE t = :trainingSession")
                .setParameter("trainingSession", trainingSession)
                .executeUpdate();
    }

    @Override
    public void deleteExercises() {
        JPAEMProvider.getEntityManager().createQuery("DELETE FROM PersonalBest").executeUpdate();
        JPAEMProvider.getEntityManager().createQuery("DELETE FROM TrainingSession").executeUpdate();
        JPAEMProvider.getEntityManager().createQuery("DELETE FROM TrainingTemplate ").executeUpdate();
        JPAEMProvider.getEntityManager().createQuery("DELETE FROM Set").executeUpdate();
        JPAEMProvider.getEntityManager().createQuery("DELETE FROM Exercise").executeUpdate();
        JPAEMProvider.getEntityManager().createQuery("DELETE FROM ExerciseType").executeUpdate();
    }

    @Override
    public void deleteExercise(int exerciseId) {
        JPAEMProvider.getEntityManager().createQuery("DELETE FROM PersonalBest pb WHERE pb.exercise.exerciseId = :exerciseId")
                .setParameter("exerciseId", exerciseId)
                .executeUpdate();
        JPAEMProvider.getEntityManager().createQuery("DELETE FROM Set s WHERE s.exercise.exerciseId = :exerciseId")
                .setParameter("exerciseId", exerciseId)
                .executeUpdate();
        JPAEMProvider.getEntityManager().createQuery("DELETE FROM Exercise e WHERE e.exerciseId = :exerciseId")
                .setParameter("exerciseId", exerciseId)
                .executeUpdate();
    }

    @Override
    public void deleteTrainingTemplate(int templateId) {
        JPAEMProvider.getEntityManager().createQuery("DELETE FROM TrainingTemplate t WHERE t.id = :templateId")
                .setParameter("templateId", templateId)
                .executeUpdate();
    }

    @Override
    public void deleteSets(Collection<Set> sets) {
        if (sets == null) {
            return;
        }
        var query = JPAEMProvider.getEntityManager().createQuery("DELETE FROM Set s WHERE s = :set");
        for (Set set : sets) {
            query.setParameter("set", set).executeUpdate();
            updatePersonalBests(set.getId().getExerciseId(), set.getTrainingSession().getUser().getUserId());
        }
    }

    @Override
    public void deleteUser(int userId) {
        User user = getUserById(userId);
        if (user == null) {
            return;
        }
        for (var ts : user.getTrainingSessions()) {
            deleteTrainingSession(ts);
        }
        for (var tt : user.getTrainingTemplates()) {
            deleteTrainingTemplate(tt.getId());
        }
        JPAEMProvider.getEntityManager().createQuery("DELETE FROM PersonalBest pb WHERE pb.user.userId = :userId")
                .setParameter("userId", userId)
                .executeUpdate();
        JPAEMProvider.getEntityManager().createQuery("DELETE FROM User u WHERE u.userId = :userId")
                .setParameter("userId", userId)
                .executeUpdate();
    }

    @Override
    public boolean userAllowedToSeeTrainingSession(int userId, int trainingSessionId) {
        User user = JPAEMProvider.getEntityManager().find(User.class, userId);
        if (user == null) {
            return false;
        }
        return user.getTrainingSessions().stream().anyMatch(ts -> ts.getTrainingSessionId() == trainingSessionId);
    }

    public List<User> getUsersWith5Sessions() {
        return JPAEMProvider.getEntityManager().createQuery(
                "SELECT u FROM User u WHERE SIZE(u.trainingSessions) >= 5", User.class)
                .getResultList();
    }

    @Override
    public boolean userHasTemplate(int userId, int templateId) {
        User user = JPAEMProvider.getEntityManager().find(User.class, userId);
        if (user == null) {
            return false;
        }
        return user.getTrainingTemplates().stream().anyMatch(t -> t.getId() == templateId);
    }

    @Override
    public User getUserById(int id) {
        return JPAEMProvider.getEntityManager().find(User.class, id);
    }

    @Override
    public User getUserByEmail(String email) {
        List<User> users = JPAEMProvider.getEntityManager().createNamedQuery("User.findByEmail", User.class)
                .setParameter("email", email)
                .getResultList();
        if (users.isEmpty()) {
            return null;
        }
        return users.getFirst();
    }

    @Override
    public Set getSetById(Set.SetId id) {
        return JPAEMProvider.getEntityManager().find(Set.class, id);
    }

    @Override
    public SetType getSetTypeById(int id) {
        if (setTypes.containsKey(id)) {
            return setTypes.get(id);
        }
        SetType setType = JPAEMProvider.getEntityManager().find(SetType.class, id);
        if (setType == null) {
            return null;
        }
        setTypes.put(id, setType);
        return setType;
    }

    @Override
    public Exercise getExerciseById(int id) {
        return JPAEMProvider.getEntityManager().find(Exercise.class, id);
    }

    @Override
    public TrainingSession getTrainingSessionById(int id) {
        return JPAEMProvider.getEntityManager().find(TrainingSession.class, id);
    }

    @Override
    public TrainingTemplate getTrainingTemplateById(int id) {
        return JPAEMProvider.getEntityManager().find(TrainingTemplate.class, id);
    }

    @Override
    public ExerciseType getExerciseTypeById(int id) {
        return JPAEMProvider.getEntityManager().find(ExerciseType.class, id);
    }

    @Override
    public List<ExerciseType> getAllExerciseTypes() {
        return JPAEMProvider.getEntityManager().createQuery("SELECT et FROM ExerciseType et", ExerciseType.class).getResultList();
    }

    @Override
    public List<PersonalBest> getPersonalBests(int exerciseId, int userId) {
        return JPAEMProvider.getEntityManager().createQuery(
                        "SELECT pb FROM PersonalBest pb WHERE pb.exercise.exerciseId = :e_id AND pb.user.userId = :u_id ORDER BY pb.id.bestReps ASC", PersonalBest.class)
                .setParameter("e_id", exerciseId)
                .setParameter("u_id", userId)
                .getResultList();
    }

    @Override
    public List<Exercise> getAllExercises() {
        return JPAEMProvider.getEntityManager().createQuery("SELECT e FROM Exercise e", Exercise.class).getResultList();
    }

    @Override
    public Map<String, List<Exercise>> getSortedExercises() {
        List<Exercise> exercises = getAllExercises();
        Map<String, List<Exercise>> sortedExercises = new HashMap<>();
        for (Exercise exercise : exercises) {
            if (!sortedExercises.containsKey(exercise.getExerciseType().getName())) {
                ArrayList<Exercise> list = new ArrayList<>();
                list.add(exercise);
                sortedExercises.put(exercise.getExerciseType().getName(), list);
            } else {
                sortedExercises.get(exercise.getExerciseType().getName()).add(exercise);
            }
        }
        return sortedExercises;
    }

    @Override
    public void updatePersonalBests(int exerciseId, int userId) {
        List<PersonalBest> personalBests = getPersonalBests(exerciseId, userId);
        for (int i = 1; i <= 12; i++) {
            Set bestSet = getBestSet(exerciseId, userId, i);
            if (personalBests.size() < i) {
                if (bestSet == null) {
                    break;
                }
                PersonalBest pb = new PersonalBest();
                pb.setId(new PersonalBest.PersonalBestId(i, exerciseId, userId));
                pb.setBestWeight(bestSet.getWeight());
                pb.setExercise(bestSet.getExercise());
                pb.setUser(bestSet.getTrainingSession().getUser());
                pb.setAchievementDate(DatetimeUtil.trimDate(bestSet.getTrainingSession().getStartTime()));
                save(pb);
            } else {
                PersonalBest pb = personalBests.get(i - 1);
                if (bestSet == null) {
                    JPAEMProvider.getEntityManager().remove(pb);
                } else {
                    pb.setBestWeight(bestSet.getWeight());
                    pb.setAchievementDate(DatetimeUtil.trimDate(bestSet.getTrainingSession().getStartTime()));
                }
            }
        }
    }


    private Set getBestSet(int exerciseId, int userId, int reps) {
        try {
            return JPAEMProvider.getEntityManager().createQuery(
                            "SELECT s FROM Set s WHERE s.exercise.exerciseId = :e_id AND s.trainingSession.user.userId = :u_id AND s.repetitions >= :reps ORDER BY s.weight DESC", Set.class)
                    .setParameter("e_id", exerciseId)
                    .setParameter("u_id", userId)
                    .setParameter("reps", reps)
                    .setMaxResults(1)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}