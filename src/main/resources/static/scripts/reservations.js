(function ($) {
    $(function () {

        const maxRoomsNumber = 4; //count from 0

        const countFormGroup = function ($form) {
            return $form.find('.form-group').length;
        };

        const addFormGroup = function (event) {
            event.preventDefault();

            const $formGroup = $(this).closest('.form-group');
            const $multipleFormGroup = $formGroup.closest('.multiple-form-group');
            const $formGroupClone = $formGroup.clone();

            if (countFormGroup($multipleFormGroup) <= maxRoomsNumber) {
                $(this)
                    .toggleClass('btn-default btn-add btn-danger btn-remove')
                    .html('–');

                $formGroupClone.find('input').val('');

                $formGroupClone.find("select").attr({
                    'name': 'rooms[' + countFormGroup($multipleFormGroup) + '].roomNumber',
                    'id': 'rooms' + countFormGroup($multipleFormGroup) + '.roomNumber'
                });

                $formGroupClone.insertAfter($formGroup);

            } else {
                const $lastFormGroupLast = $multipleFormGroup.find('.form-group:last');
                $lastFormGroupLast.find('span').attr('data-tooltip', 'Maximum number of rooms has been reached')
                $lastFormGroupLast.find('.btn-add').attr('disabled', true);
            }


        };

        const removeFormGroup = function (event) {
            event.preventDefault();

            const $formGroup = $(this).closest('.form-group');
            const $multipleFormGroup = $formGroup.closest('.multiple-form-group');

            const $lastFormGroupLast = $multipleFormGroup.find('.form-group:last');
            $lastFormGroupLast.find('.btn-add').attr('disabled', false);
            $lastFormGroupLast.find('span').removeAttr('data-tooltip');
            $formGroup.remove();
        };


        $(document).on('click', '.btn-add', addFormGroup);
        $(document).on('click', '.btn-remove', removeFormGroup);

        $('[type="date"]').prop('min', function(){
            return new Date().toJSON().split('T')[0];
        });

    });
})(jQuery);
