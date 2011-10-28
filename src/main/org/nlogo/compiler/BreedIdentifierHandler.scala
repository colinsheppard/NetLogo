// (C) 2011 Uri Wilensky. https://github.com/NetLogo/NetLogo

package org.nlogo.compiler
import org.nlogo.agent.AgentSet
import org.nlogo.api.{Program,Token,TokenType}
import org.nlogo.nvm.Instruction
// The Helper class and some of the methods aren't private because we want to get at them from
// TestBreedIdentifierHandler. - ST 12/22/08
private object BreedIdentifierHandler {
  import org.nlogo.prim._
  import TokenType.COMMAND
  import TokenType.REPORTER
  def process(token:Token,program:Program):Option[Token] = {
    val handlers = if(program.is3D) handlers3D else handlers2D
    handlers.toStream.flatMap(_.process(token,program)).headOption
  }
  def turtle(patternString:String,tokenType:TokenType,singular:Boolean,primClass:Class[_ <: Instruction]) =
    new Helper(patternString,tokenType,singular,primClass,
               _.breeds, _.breedsSingular, (obj:AnyRef) => true)
  def directedLink(patternString:String,tokenType:TokenType,singular:Boolean,primClass:Class[_ <: Instruction]) =
    new Helper(patternString,tokenType,singular,primClass,
               _.linkBreeds, _.linkBreedsSingular,
               { case a:AgentSet => a.isDirected
                case s:String => s == "DIRECTED-LINK-BREED" } )
  def undirectedLink(patternString:String,tokenType:TokenType,singular:Boolean,primClass:Class[_ <: Instruction]) =
    new Helper(patternString,tokenType,singular,primClass,
               _.linkBreeds, _.linkBreedsSingular,
               { case a:AgentSet => a.isUndirected
                case s:String => s == "UNDIRECTED-LINK-BREED" } ) 
  private val handlers2D = handlers(false)
  private val handlers3D = handlers(true)
  private def handlers(is3D:Boolean) = List(
    // prims for turtle breeds
    turtle("CREATE-*", COMMAND, false, classOf[_createturtles]),
    turtle("CREATE-ORDERED-*", COMMAND, false, classOf[_createorderedturtles]),
    turtle("HATCH-*", COMMAND, false, classOf[_hatch]),
    turtle("SPROUT-*", COMMAND, false,classOf[_sprout]),
    turtle("IS-*?", REPORTER, true,classOf[_isbreed]),
    turtle("*-HERE", REPORTER, false,classOf[_breedhere]),
    turtle("*-ON", REPORTER, false,classOf[_breedon]),
    turtle("*", REPORTER, false, classOf[_breed]),
    turtle("*", REPORTER, true, classOf[_breedsingular]),
    // if we're in 3D point to the 3D version since
    // the syntax is different in 3D ev 12/11/06
    turtle("*-AT", REPORTER, false,
           if(is3D) classOf[org.nlogo.prim.threed._breedat]
           else classOf[_breedat]),
    // prims for link breeds
    directedLink("*", REPORTER, true, classOf[_linkbreedsingular]),
    undirectedLink("*", REPORTER, true, classOf[_linkbreedsingular]),
    directedLink("*", REPORTER, false, classOf[_linkbreed]),
    undirectedLink("*", REPORTER, false, classOf[_linkbreed]),
    directedLink("IS-*?", REPORTER, true,classOf[_isbreed]),
    undirectedLink("IS-*?", REPORTER, true,classOf[_isbreed]),
    directedLink("CREATE-*-FROM", COMMAND, true,classOf[_createlinkfrom]),
    directedLink("CREATE-*-FROM", COMMAND, false,classOf[_createlinksfrom]),
    directedLink("CREATE-*-TO", COMMAND, true,classOf[_createlinkto]),
    directedLink("CREATE-*-TO", COMMAND, false,classOf[_createlinksto]),
    undirectedLink("CREATE-*-WITH", COMMAND, true,classOf[_createlinkwith]),
    undirectedLink("CREATE-*-WITH", COMMAND, false,classOf[_createlinkswith]),
    directedLink("IN-*-NEIGHBOR?", REPORTER, true,classOf[_inlinkneighbor]),
    directedLink("OUT-*-NEIGHBOR?", REPORTER, true,classOf[_outlinkneighbor]),
    directedLink("IN-*-FROM", REPORTER, true,classOf[_inlinkfrom]),
    directedLink("OUT-*-TO", REPORTER, true,classOf[_outlinkto]),
    directedLink("OUT-*-NEIGHBORS", REPORTER, true,classOf[_outlinkneighbors]),
    directedLink("IN-*-NEIGHBORS", REPORTER, true,classOf[_inlinkneighbors]),
    directedLink("MY-IN-*", REPORTER, false,classOf[_myinlinks]),
    directedLink("MY-OUT-*", REPORTER, false,classOf[_myoutlinks]),
    undirectedLink("*-NEIGHBORS", REPORTER, true,classOf[_linkneighbors]),
    undirectedLink("MY-*", REPORTER, false,classOf[_mylinks]),
    undirectedLink("*-WITH", REPORTER, true,classOf[_linkwith]),
    undirectedLink("*-NEIGHBOR?", REPORTER, true,classOf[_linkneighbor]) 
  )
  class Helper
    (patternString:String,tokenType:TokenType,singular:Boolean,primClass:Class[_ <: Instruction],
     breeds:(Program)=>java.util.Map[String,Object],singularMap:(Program)=>java.util.Map[String,String],
     isValidBreed:(AnyRef)=>Boolean)
  {
    import java.util.regex.Pattern
    val pattern = Pattern.compile("\\A"+patternString.replaceAll("\\?","\\\\?").replaceAll("\\*","(.+)")+"\\Z")
    def process(tok:Token,program:Program):Option[Token] = {
      val matcher = pattern.matcher(tok.value.asInstanceOf[String])
      if(!matcher.matches()) return None
      val name = matcher.group(1)
      val map = if(singular) singularMap(program) else breeds(program)
      if(!map.containsKey(name)) return None
      val breedName = if(singular) map.get(name) else name
      if(!isValidBreed(breeds(program).get(breedName))) return None
      val instr = Instantiator.newInstance[Instruction](primClass,breedName)
      val tok2 = new Token(tok.value.asInstanceOf[String],tokenType,instr)(tok.startPos,tok.endPos,tok.fileName)
      instr.token(tok2)
      Some(tok2)
    }
  }
}
