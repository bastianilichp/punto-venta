/* global PrimeFaces */
/*https://github.com/primefaces/primefaces/issues/7271*/
if (PrimeFaces.widget.InputNumber) {
    PrimeFaces.widget.InputNumber.prototype.bindInputEvents = function() {
        var $this = this;

        // GitHub #6447: browser auto fill fix
        this.input.off('blur.inputnumber').on('blur.inputnumber', function(e) {
            var element = AutoNumeric.getAutoNumericElement(this);
            if (element && this.value && this.value.length > 0) {
                var newValue = this.value;
                if ($this.cfg.digitGroupSeparator) {
                    newValue = newValue.replaceAll($this.cfg.digitGroupSeparator, '');
                }
                element.set(newValue, null, true);
            }
            $this.copyValueToHiddenInput();
        });
    }
}


