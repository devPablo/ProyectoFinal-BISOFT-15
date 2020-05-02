function dragMoveListener(event) {
    var target = event.target
    // keep the dragged position in the data-x/data-y attributes
    var x = (parseFloat(target.getAttribute('data-x')) || 0) + event.dx
    var y = (parseFloat(target.getAttribute('data-y')) || 0) + event.dy

    // translate the element
    target.style.webkitTransform =
        target.style.transform =
        'translate(' + x + 'px, ' + y + 'px)'

    // update the posiion attributes
    target.setAttribute('data-x', x)
    target.setAttribute('data-y', y)
}

interact('.drag-drop')
    .draggable({
        inertia: true,
        modifiers: [
            interact.modifiers.restrictRect({
                restriction: '.dropzone',
                endOnly: true
            })
        ],
        autoScroll: false,
        // dragMoveListener from the dragging demo above
        listeners: { move: dragMoveListener }
    })




interact('.dropzone').dropzone({
    // only accept elements matching this CSS selector
    accept: '.drag-drop',
    // Require a 75% element overlap for a drop to be possible
    overlap: 0.65,

    // listen for drop related events:

    ondropactivate: function (event) {
        // add active dropzone feedback
        event.target.classList.add('drop-active');
    },
    ondragenter: function (event) {
        var draggableElement = event.relatedTarget
        var dropzoneElement = event.target

        // feedback the possibility of a drop
        dropzoneElement.classList.add('drop-target')
        draggableElement.classList.add('can-drop')

        draggableElement.classList.add('dragdrop_hover');
    },
    ondragleave: function (event) {
        // remove the drop feedback style
        event.target.classList.remove('drop-target')
        event.relatedTarget.classList.remove('can-drop')

        event.relatedTarget.classList.remove('dragdrop_hover');
    },
    ondrop: function (event) {
        let completed__tasksSpace = document.querySelector('#completed__tasks-space');
        completed__tasksSpace.style = '';
        let min_one_completed = false;


        const taskly__username = document.querySelector('#taskly__username').innerText;
        getMethodToAPI(endpoint + `/${taskly__username}`).done(data => {
            for (let i = 0; i < data.length; i++) {
                if (data[i].complete) {
                    min_one_completed = true;
                    break;
                }
            }

            if (!min_one_completed) {
                completed__tasksSpace.innerHTML = '';
            }
            completed__tasksSpace.appendChild(event.relatedTarget);
            completed__tasksSpace.classList.remove('vertical-align');
            event.relatedTarget.classList.remove('dragdrop_hover');
            event.relatedTarget.classList.remove('drag-drop');
            event.relatedTarget.style = '';
            completed__tasksSpace.classList.remove('text-center');

            const uuid = event.relatedTarget.getAttribute('uuid');
            const UPDATED_TASK =
            {
                "complete": true
            }
            putMethodToAPI(endpoint + `/${uuid}`, UPDATED_TASK).done(e => {
                fillTasks();
            });


        });
    },
    ondropdeactivate: function (event) {
        // remove active dropzone feedback
        event.target.classList.remove('drop-active')
        event.target.classList.remove('drop-target')
    }
})