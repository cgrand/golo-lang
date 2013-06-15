/*
 * Copyright 2012-2013 Institut National des Sciences Appliquées de Lyon (INSA-Lyon)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package gololang;

import org.testng.annotations.Test;

import java.lang.invoke.MethodHandle;
import java.util.Collections;
import java.util.TreeMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TemplateEngineTest {

  @Test
  public void simple_string() throws Throwable {
    TemplateEngine engine = new TemplateEngine();
    MethodHandle tpl = engine.compile("Plop!");
    assertThat((String) tpl.invoke(null), is("Plop!"));
  }

  @Test
  public void simple_value() throws Throwable {
    TemplateEngine engine = new TemplateEngine();
    MethodHandle tpl = engine.compile("<%= params: getOrElse(\"a\", \"n/a\")%>!");
    assertThat((String) tpl.invoke(Collections.emptyMap()), is("n/a!"));
    assertThat((String) tpl.invoke(new TreeMap<String, String>() {
      {
        put("a", "Plop!");
      }
    }), is("Plop!!"));
  }

  @Test
  public void simple_repeat() throws Throwable {
    TemplateEngine engine = new TemplateEngine();
    String template = "<% for (var i = 0, i < 3, i = i + 1) { %>a<% } %>";
    MethodHandle tpl = engine.compile(template);
    assertThat((String) tpl.invoke(null), is("aaa"));
  }
}
