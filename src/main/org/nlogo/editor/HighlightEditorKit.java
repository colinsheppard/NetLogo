// (C) 2011 Uri Wilensky. https://github.com/NetLogo/NetLogo

package org.nlogo.editor;

strictfp class HighlightEditorKit<TokenType>
    extends javax.swing.text.DefaultEditorKit
    implements
    javax.swing.text.ViewFactory,
    javax.swing.event.DocumentListener {

  private final java.awt.event.TextListener listener; // allowed to be null
  private javax.swing.JEditorPane pane;
  private final Colorizer<TokenType> colorizer;

  HighlightEditorKit(java.awt.event.TextListener listener,
                     Colorizer<TokenType> colorizer) {
    this.listener = listener;
    this.colorizer = colorizer;
  }

  @Override
  public void install(javax.swing.JEditorPane pane) {
    this.pane = pane;
  }

  @Override
  public javax.swing.text.ViewFactory getViewFactory() {
    return this;
  }

  public javax.swing.text.View create(javax.swing.text.Element elem) {
    return new HighlightView<TokenType>(pane, elem, colorizer);
  }

  @Override
  public javax.swing.text.Document createDefaultDocument() {
    javax.swing.text.PlainDocument doc = new javax.swing.text.PlainDocument();
    doc.addDocumentListener(this);
    return doc;
  }

  public void insertUpdate(javax.swing.event.DocumentEvent e) {
    if (listener != null) {
      listener.textValueChanged(null);
    }
  }

  public void removeUpdate(javax.swing.event.DocumentEvent e) {
    if (listener != null) {
      listener.textValueChanged(null);
    }
  }

  public void changedUpdate(javax.swing.event.DocumentEvent e) {
    if (listener != null) {
      listener.textValueChanged(null);
    }
  }

}
