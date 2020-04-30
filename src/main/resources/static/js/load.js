const days = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
const months = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];

const endpoint = `https://bisoft15.azurewebsites.net/api/task`;
let FLAG_NEW_TASK_FORM = false;

$(document).ready(() => {
    // Load current date
    // Format: Sunday, April 26th, 2020
    let todayDate = formatDate(new Date());
    document.querySelector('#middle__date').textContent = todayDate;

    fillTasks();


});


function fillTasks() {
    const loading =
        `
        <div id="taskly_loading">
            <div>
                <img src="/img/loading.gif">
            </div>
        </div>
        `
    document.querySelector('#middle_tasks').innerHTML = loading;

    const userId = 1;
    return getMethodToAPI(endpoint + `/${userId}`).done(data => {
        const tasks_todo = [];
        const tasks_done = [];

        data.forEach(e => {
            if (e.complete) {
                tasks_done.push(e);
            } else {
                tasks_todo.push(e);
            }
        });


        // Completed Tasks
        let completed__tasksSpace = document.querySelector('#completed__tasks-space');

        if (tasks_done.length > 0) {
            completed__tasksSpace.style = '';
            completed__tasksSpace.innerHTML = '';
            completed__tasksSpace.classList.remove('vertical-align');
            completed__tasksSpace.classList.remove('text-center');

            let task__uuid;
            let task__name;
            let task__description;
            let task__dueDate;
            let task__dueTime;
            let task_card;

            for (let i = 0; i < tasks_done.length; i++) {

                task__uuid = tasks_done[i].uuid;
                task__name = tasks_done[i].name;
                task__description = tasks_done[i].description;
                task__dueDate = formatDate(new Date(tasks_done[i].dueDate));
                task__dueTime = formatTime(new Date(tasks_done[i].dueDate));

                task_card = `
                        <div>
                        <div class="task_card drag-drop" uuid="${task__uuid}">
                            <div class="row">
                                <div class="col-sm-1 taskly_align-middle">
                                    <div class="task__delete">X</div>
                                </div>
                                <div class="col-lg-8">
                                    <div><span class="task__title">${task__name}</span></div>
                                    <div><span class="task__description">${task__description}</span></div>
                                    <div><span class="task__dueDate">${task__dueDate}</span></div>
                                </div>
                                <div class="col-sm-3 taskly_align-middle">
                                    <div><span class="task_dueTime">${task__dueTime}</span></div>
                                </div>
                            </div>
                        </div>
                        </div>
                        `

                completed__tasksSpace.innerHTML += task_card;

            }
        } else {
            completed__tasksSpace.innerHTML = '';
            let empty_task =
                `
                <div>
                    <span class="title">Completed Tasks</span>
                </div>
                <div>
                    <span class="subtitle">Drag and drop the tasks you've finished</span>
                </div>
                `
            completed__tasksSpace.innerHTML += empty_task;
            completed__tasksSpace.classList.add('vertical-align');
            completed__tasksSpace.classList.add('text-center');
        }

















        // TODO Tasks
        const middle_tasks = document.querySelector('#middle_tasks');
        middle_tasks.innerHTML = '';
        middle_tasks.style.textAlign = 'justify';
        if (tasks_todo.length > 0) {

            for (let i = 0; i < tasks_todo.length; i++) {

                task__uuid = tasks_todo[i].uuid;
                task__name = tasks_todo[i].name;
                task__description = tasks_todo[i].description;
                task__dueDate = formatDate(new Date(tasks_todo[i].dueDate));
                task__dueTime = formatTime(new Date(tasks_todo[i].dueDate));

                task_card = `
                        <div>
                        <div class="task_card drag-drop" uuid="${task__uuid}">
                            <div class="row">
                                <div class="col-sm-1 taskly_align-middle">
                                    <div class="task__delete">X</div>
                                </div>
                                <div class="col-lg-8">
                                    <div><span class="task__title">${task__name}</span></div>
                                    <div><span class="task__description">${task__description}</span></div>
                                    <div><span class="task__dueDate">${task__dueDate}</span></div>
                                </div>
                                <div class="col-sm-3 taskly_align-middle">
                                    <div><span class="task_dueTime">${task__dueTime}</span></div>
                                </div>
                            </div>
                        </div>
                        </div>
                        `

                middle_tasks.innerHTML += task_card;

            }
        } else {
            middle_tasks.style.textAlign = 'center';
            let middle_tasks_empty = `
            <div>
                <img src="/img/taskly_no-tasks.svg" alt="">
            </div>
            <div id="middle_tasks-sub" class="text-center">
                <div>
                    <span class="title">You don't have any task yet!</span>
                </div>
                <div>
                    <span class="subtitle">Begin by adding a new task</span>
                </div>
            </div>
            `

            middle_tasks.innerHTML += middle_tasks_empty;
        }

        if (tasks_done.length > 0 || tasks_todo.length > 0) {
            // Add delete listener
            document.querySelectorAll('.task__delete').forEach(e => {
                e.addEventListener('click', f => {
                    const uuid = f.target.parentNode.parentNode.parentNode.getAttribute('uuid');
                    deleteMethodToAPI(endpoint + `/${uuid}`).done(f => {
                        fillTasks();
                    });
                });
            });
        }
    });
}

function formatDate(date) {
    let day = days[date.getDay()]
    let month = months[date.getUTCMonth()];
    let day_num = date.getDate();
    let nth = (day_num == 1) ? "st" : (day_num == 2) ? "nd" : (day_num == 3) ? "rd" : "th";
    let year = date.getFullYear();
    return `${day}, ${month} ${day_num}${nth}, ${year}`;
}

function formatTime(date) {
    let daytime = (date.getHours() >= 12) ? "PM" : "AM";
    let hour = (daytime == "PM") ? ("0" + (date.getHours() - 12).toString()).slice(-2) : ("0" + (date.getHours()).toString()).slice(-2);
    let minute = (date.getMinutes().toString().length == 2) ? date.getMinutes() : '0' + date.getMinutes().toString();
    return `${hour}:${minute} ${daytime}`;
}

document.querySelector('body').addEventListener('keydown', e => {
    if (e.key == 'Escape') {
        if (FLAG_NEW_TASK_FORM) {
            document.querySelector('#taskly_new-task').classList.add('d-none');
            FLAG_NEW_TASK_FORM = false;
        }
    }
});

document.querySelector('#taskly__task').addEventListener('click', e => {
    document.querySelector('#taskly_new-task').classList.remove('d-none');
    FLAG_NEW_TASK_FORM = true;

    document.querySelector('#taskly__form__name').value = '';
    document.querySelector('#taskly__form__name').focus();
    document.querySelector('#taskly__form__description').value = '';
    document.querySelector('#taskly__form__duedate').value = '';
});

document.querySelector('#taskly__form__submit').addEventListener('click', e => {
    let name = document.querySelector('#taskly__form__name').value;
    let description = document.querySelector('#taskly__form__description').value;
    let dueDate = document.querySelector('#taskly__form__duedate').value;

    document.querySelector('#taskly_new-task').classList.add('d-none');
    FLAG_NEW_TASK_FORM = false;

    const NEW_TASK =
    {
        "userId": "1",
        "name": name,
        "description": description,
        "dueDate": dueDate
    }

    postMethodToAPI(endpoint, NEW_TASK).done(() => {
        fillTasks();
    });


});