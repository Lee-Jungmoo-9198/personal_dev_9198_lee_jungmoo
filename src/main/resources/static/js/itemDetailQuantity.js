document.querySelectorAll('.quantity-btn').forEach(button => {
    button.addEventListener('click', function() {
        const input = document.getElementById('quantity-input');
        let value = parseInt(input.value);
        
        if (this.dataset.type === 'minus' && value > 1) {
            value--;
        } else if (this.dataset.type === 'plus') {
            value++;
        }
        
        input.value = value;
    });
});
