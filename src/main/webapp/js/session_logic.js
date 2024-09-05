let exerciseCount = 0;

function addExercise() {
    const exercises = document.getElementById('exercises');
    const exerciseId = document.getElementById('exerciseSelect').value;
    const exerciseName = document.getElementById('exerciseSelect').options[document.getElementById('exerciseSelect').selectedIndex].text;

    let existing = document.getElementById(`exercise-${exerciseId}`);
    if (existing) {
        return;
    }

    const exerciseDiv = document.createElement('div');
    exerciseDiv.id = `exercise-${exerciseId}`;
    exerciseDiv.classList.add('exercise');
    exerciseDiv.innerHTML = `
                <div class="exercise-header">
                <h3 class="exercise-name">${exerciseName}</h3>
                <div class="exercise-buttons">
                <button type="button" onclick="addSet(${exerciseId})">+</button>
                <button type="button" onclick="removeExercise(${exerciseId})">-</button>
                </div>
                </div>
                <div id="sets-${exerciseId}" class="sets">
                </div>
            `;

    exercises.appendChild(exerciseDiv);
    exerciseCount++;
}

function removeExercise(exerciseId) {
    const exerciseDiv = document.getElementById(`exercise-${exerciseId}`);
    let numChildren = exerciseDiv.children.length
    if (numChildren === 1) {
        exerciseDiv.remove();
    } else {
        let numSets = exerciseDiv.children[1].children.length;
        if (numSets === 1 || numSets === 0) {
            exerciseDiv.remove();
        } else {
            exerciseDiv.children[1].children[numSets - 1].remove();
        }
    }
    exerciseCount--;
}

function addSet(exerciseId) {
    const setsDiv = document.getElementById(`sets-${exerciseId}`);
    const setId = setsDiv.children.length + 1;

    const setDiv = document.createElement('div');
    setDiv.id = `set-${exerciseId}-${setId}`;
    setDiv.classList.add('set');
    setDiv.innerHTML = `
                <label>Weight:</label>
                <input type="number" name="${exerciseId}#${setId}#weight" step="0.5" min="0" required>
                <label>Reps:</label>
                <input type="number" name="${exerciseId}#${setId}#reps" min="1" required>
                <label>Set Type:</label>
                <select name="${exerciseId}#${setId}#setType" required>
                    <option value="1">Regular</option>
                    <option value="2">Drop set</option>
                    <option value="3">Superset</option>
                    <option value="4">To failure</option>
                </select>
                <button type="button" onclick="removeSet(${exerciseId}, ${setId})">-</button>
            `;

    setsDiv.appendChild(setDiv);
}

function removeSet(exerciseId, setId) {
    const setDiv = document.getElementById(`set-${exerciseId}-${setId}`);
    setDiv.remove();
}