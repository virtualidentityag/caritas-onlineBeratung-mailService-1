package de.caritas.cob.mailservice.api.helper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

import de.caritas.cob.mailservice.api.model.TemplateDataDTO;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.hamcrest.collection.IsMapContaining;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TemplateDataConverterTest {

  private TemplateDataConverter templateDataConverter;
  @Mock private Helper helper;

  private final String KEY1 = "key1";
  private final String VALUE1 = "value1";
  private final String KEY2 = "key2";
  private final String VALUE2 = "value2";
  private final List<TemplateDataDTO> TEMPLATE_DTO_LIST =
      Arrays.asList(
          new TemplateDataDTO().key(KEY1).value(VALUE1),
          new TemplateDataDTO().key(KEY2).value(VALUE2));

  @BeforeEach
  void setup() {
    this.templateDataConverter = new TemplateDataConverter(helper);
  }

  @Test
  void convertFromTemplateDataDTOList_Should() {

    when(helper.removeHTMLFromText(Mockito.eq(VALUE1))).thenReturn(VALUE1);
    when(helper.removeHTMLFromText(Mockito.eq(VALUE2))).thenReturn(VALUE2);
    when(helper.unescapeHtml(Mockito.eq(VALUE1))).thenReturn(VALUE1);
    when(helper.unescapeHtml(Mockito.eq(VALUE2))).thenReturn(VALUE2);

    Map<String, Object> result =
        templateDataConverter.convertFromTemplateDataDTOList(TEMPLATE_DTO_LIST);

    assertThat(result, IsMapContaining.hasEntry(KEY1, VALUE1));
    assertThat(result, IsMapContaining.hasEntry(KEY2, VALUE2));
  }
}
