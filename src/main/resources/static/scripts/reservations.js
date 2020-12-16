(function ($) {
    $(function () {

        var addFormGroup = function (event) {
            event.preventDefault();

            var $formGroup = $(this).closest('.form-group');
            var $multipleFormGroup = $formGroup.closest('.multiple-form-group');
            var $formGroupClone = $formGroup.clone();

            if (countFormGroup($multipleFormGroup)<=4){
                $(this)
                    .toggleClass('btn-default btn-add btn-danger btn-remove')
                    .html('–');

                $formGroupClone.find('input').val('');

                $formGroupClone.find("select").attr({
                    'name' : 'rooms['+countFormGroup($multipleFormGroup)+'].roomNumber',
                    'id' : 'rooms'+countFormGroup($multipleFormGroup)+'.roomNumber'});

                // console.log("formGroupClone: "+$formGroupClone.html());
                // console.log('fields number: '+countFormGroup($multipleFormGroup));

                $formGroupClone.insertAfter($formGroup);


                var $lastFormGroupLast = $multipleFormGroup.find('.form-group:last');
                if ($multipleFormGroup.data('max') <= countFormGroup($multipleFormGroup)) {
                    $lastFormGroupLast.find('.btn-add').attr('disabled', true);
                }
            }else{
                // $('#multiple-rooms-input').setCustomValidity('Maximum number of rooms has been reached.');
                // console.log($('#multiple-rooms-input').html());

                var $lastFormGroupLast = $multipleFormGroup.find('.form-group:last');
                $lastFormGroupLast.find('.btn-add').attr('disabled', true);
                // $lastFormGroupLast.find('.btn-add').get(0).setCustomValidity('Maximum number of rooms has been reached.');
                // $lastFormGroupLast.find('.btn-add').get(0).reportValidity();
                // $lastFormGroupLast.find('.input-group-btn').tooltip();
            }


        };

        var removeFormGroup = function (event) {
            event.preventDefault();

            var $formGroup = $(this).closest('.form-group');
            var $multipleFormGroup = $formGroup.closest('.multiple-form-group');

            var $lastFormGroupLast = $multipleFormGroup.find('.form-group:last');
            if ($multipleFormGroup.data('max') >= countFormGroup($multipleFormGroup)) {
                $lastFormGroupLast.find('.btn-add').attr('disabled', false);
            }

            $formGroup.remove();
        };

        var countFormGroup = function ($form) {
            return $form.find('.form-group').length;
        };

        $(document).on('click', '.btn-add', addFormGroup);
        $(document).on('click', '.btn-remove', removeFormGroup);

    });
})(jQuery);
