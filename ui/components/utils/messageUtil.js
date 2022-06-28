export const showSuccessfulMessage = (addToast, t) => {
  addToast(t('setting.applied'), {
    appearance: 'success',
    autoDismiss: true,
  });
};
