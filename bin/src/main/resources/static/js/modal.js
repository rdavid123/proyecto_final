const btnOpenModal = document.getElementById('open-modal-btn');
const modal = document.querySelector('.modal');
const btnCloseModal = document.getElementById('close--modal');

btnOpenModal.addEventListener('click', (e)=>{
    e.preventDefault();
    modal.classList.toggle('modal--show');
})

btnCloseModal.addEventListener('click', ()=>{
    modal.classList.remove('modal--show');
})

// window.onclick = function (event) {
//     if (event.target.classList.contains('modal')) {
//         event.target.style.display = "none";
//     }
// }