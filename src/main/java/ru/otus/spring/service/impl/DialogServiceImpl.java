package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ru.otus.spring.dto.CategoryDto;
import ru.otus.spring.dto.ContactDto;
import ru.otus.spring.dto.DialogDto;
import ru.otus.spring.model.Dialog;
import ru.otus.spring.model.Message;
import ru.otus.spring.model.Tenant;
import ru.otus.spring.model.Widget;
import ru.otus.spring.repository.DialogRepository;
import ru.otus.spring.service.DialogService;
import ru.otus.spring.service.MessageService;
import ru.otus.spring.service.UserService;
import ru.otus.spring.service.WidgetService;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DialogServiceImpl implements DialogService {


  public static final String DIALOG_NOT_FOUND = "Dialog not found";
  public static final String FULL_NAME = "Обращение с сайта";
  public static final String DEFAULT_DIALOG_TYPE = "site";
  public static final String MAIL_FROM = "noreply@sitepower.io";
  public static final String DATE_FORMAT = "dd.MM.yyyy HH:mm:ss";

  private final DialogRepository dialogRepository;
  private final UserService userService;
  private final WidgetService widgetService;

  @Lazy
  @Autowired
  private MessageService messageService;

  private final ModelMapper mapper;

  private final JavaMailSender emailSender;

  @Override
  public DialogDto getOrCreateDialog(DialogDto dialogDto) {
    Dialog dialog = StringUtils.isEmpty(dialogDto.getDialogId()) ? new Dialog() : dialogRepository.findById(dialogDto.getDialogId()).orElseThrow(() -> new RuntimeException(DIALOG_NOT_FOUND));
    Widget widget = widgetService.getWidget(dialogDto.getWidgetId());
    if (dialog.getId() == null) {
      dialog.setWidget(widget);
      dialog.setCreatedDate(Instant.now());
      dialog.setFullName(FULL_NAME);
      dialog = dialogRepository.save(dialog);
    }
    dialogDto.setDialogId(dialog.getId());
    dialogDto.setWidgetId(widget.getId());
    return dialogDto;
  }

  @Override
  public Dialog getDialogById(UUID dialogId) {
    return dialogRepository.findById(dialogId).orElseThrow(() -> new RuntimeException(DIALOG_NOT_FOUND));
  }

  @Override
  public List<DialogDto> getDialogsByCurrentTenant() {
    Tenant currentTenant = userService.getCurrentUser().getTenant();
    List<Widget> widgetList = widgetService.getWidgetsByTenant(currentTenant);
    List<DialogDto> dialogDtos = new ArrayList<>();
    widgetList.forEach(widget -> {
      ArrayList<Dialog> dialogs = dialogRepository.findAllByWidget(widget);
      dialogs.forEach(dialog -> {
        if (dialog.getLastMessage() != null) {
          DialogDto dialogDto = mapper.map(dialog, DialogDto.class);
          dialogDto.setType(DEFAULT_DIALOG_TYPE);
          dialogDto.setDialogId(dialog.getId());
          dialogDto.setLastMessageBody(dialog.getLastMessage().getBody());
          dialogDto.setLastMessageCreated(dialog.getLastMessage().getCreatedDate());
          dialogDtos.add(dialogDto);
        }
      });
    });
    return dialogDtos;
  }

  @Override
  public void putDialogContacts(Dialog dialog, ContactDto contactDto) {
    dialog.setEmail(contactDto.getEmail());
    dialog.setPhone(contactDto.getPhone());
    dialog.setFullName(contactDto.getName());
    dialogRepository.save(dialog);
  }

  @Override
  public void putDialogCategory(Dialog dialog, CategoryDto categoryDto) {
    dialog.setClassName(categoryDto.getClassName());
    dialogRepository.save(dialog);
  }

  @Override
  public void putDialogLastMessage(Dialog dialog, Message message) {
    dialog.setLastMessage(message);
    dialogRepository.save(dialog);
  }

  @Override
  public void sendDialogToEmail(Dialog dialog) {
    final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT).withLocale(Locale.ENGLISH)
        .withZone(ZoneId.systemDefault());
    SimpleMailMessage mailMessage = new SimpleMailMessage();
    mailMessage.setFrom(MAIL_FROM);
    mailMessage.setTo(userService.getCurrentUser().getEmail());
    mailMessage.setSubject(dialog.getFullName());
    String mailBody = messageService.getMessages(dialog).stream().map(msg ->
        String.join("\n", dateTimeFormatter.format(
            msg.getCreatedDate()), msg.getBody())).collect(Collectors.joining("\n\n"));
    mailMessage.setText(mailBody);
    emailSender.send(mailMessage);
  }


}
