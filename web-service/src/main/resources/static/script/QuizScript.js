let selectedOption = null;

const buttons = document.querySelectorAll('.answer');
const nextButton = document.getElementById('submit');

buttons.forEach(btn => {
    btn.addEventListener('click', () => {
        if(selectedOption === btn.dataset.value){
            btn.classList.remove('active');
            selectedOption = null;
            btn.style.background = '#263142';
        } else {
            buttons.forEach (b =>{
                b.classList.remove('active');
                b.style.background = '#263142';
            })
            btn.classList.add('active');
            selectedOption = btn.dataset.value;
            btn.style.background = '#090909';
        }
        nextButton.style.display="block";
    });
});