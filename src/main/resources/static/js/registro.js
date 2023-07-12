setTimeout(function(){
    var alertaElement = document.getElementById('alerta');
    if (alertaElement) {
        alertaElement.remove();
    }
}, 3000);
(() => {
  'use strict'

  const forms = document.querySelectorAll('.needs-validation')

  Array.from(forms).forEach(form => {
    form.addEventListener('submit', event => {
      if (!form.checkValidity()) {
        event.preventDefault()
        event.stopPropagation()
      }

      form.classList.add('was-validated')
    }, false)
  })
})()