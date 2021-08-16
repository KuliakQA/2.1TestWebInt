package ru.netology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

class DebitCardTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    public void PositiveTest() {
        $("[data-test-id=name] input").setValue("Куляк Вадим");
        $("[data-test-id=phone] input").setValue("+79157894875");
        $("[data-test-id=agreement]").click();
        $("button").click();
        $("[data-test-id=order-success]")
                .shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    public void PositiveTestWithHyphen() {
        $("[data-test-id=name] input").setValue("Куляк-Косяк Вадим");
        $("[data-test-id=phone] input").setValue("+79883498522");
        $("[data-test-id=agreement]").click();
        $("button").click();
        $("[data-test-id=order-success]")
                .shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }

    @Test
    public void NegativeTestName() {
        $("[data-test-id=name] input").setValue("");
        $("[data-test-id=phone] input").setValue("+79157894875");
        $("[data-test-id=agreement]").click();
        $("button").click();
        $("[data-test-id=name].input_invalid .input__sub")
                .shouldHave(exactText(" Поле обязательно для заполнения"));
    }

    @Test
    void NegativeTestPhone(){
        $("[data-test-id=name] input").setValue("Куляк Вадим");
        $("[data-test-id=phone] input").setValue("7845478");
        $("[data-test-id=agreement]").click();
        $("button").click();
        $("[data-test-id=phone].input_invalid .input__sub")
                .shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    void NegativeTestText(){
        $("[data-test-id=name] input").setValue("Куляк Вадим");
        $("[data-test-id=phone] input").setValue("+79078445484");
        $("button").click();
        $("[data-test-id=agreement].input_invalid .checkbox__text")
                .shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй"));
    }
}
