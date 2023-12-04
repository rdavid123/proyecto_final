function openModal(id) {
    var modal = document.getElementById("modal" + id);
    modal.style.display = "block";
}

function closeModal(id) {
    var modal = document.getElementById("modal" + id);
    modal.style.display = "none";
}

// Cerrar el modal si el usuario hace clic fuera de él
window.onclick = function (event) {
    if (event.target.classList.contains('modal')) {
        event.target.style.display = "none";
    }
}