package decisions4s.example.docs

import decisions4s.*
import decisions4s.syntax.*
import decisions4s.util.*

object PullRequestDecision {

  case class Input[F[_]](numOfApprovals: F[Int], isTargetBranchProtected: F[Boolean], authorIsAdmin: F[Boolean]) derives FunctorK, SemigroupalK, PureK

  case class Output[F[_]](allowMerging: F[Boolean], notifyUnusualAction: F[Boolean]) derives FunctorK, SemigroupalK

  val decisionTable: DecisionTable[Input, Output] =
    DecisionTable(
      rules,
      inputNames = Name.auto,
      outputNames = Name.auto,
      name = "PullRequestDecision",
    )

  private def rules: List[Rule[Input, Output]] = List(
    Rule(
      matching = Input(
        numOfApprovals = it > 0,
        isTargetBranchProtected = it.isFalse,
        authorIsAdmin = it.catchAll,
      ),
      output = Output(
        allowMerging = true.asLiteral,
        notifyUnusualAction = false.asLiteral,
      ),
    ),
    Rule(
      matching = Input(
        numOfApprovals = it > 1,
        isTargetBranchProtected = it.isTrue,
        authorIsAdmin = it.catchAll,
      ),
      output = Output(
        allowMerging = true.asLiteral,
        notifyUnusualAction = false.asLiteral,
      ),
    ),
    Rule(
      matching = Input(
        numOfApprovals = it.catchAll,
        isTargetBranchProtected = it.catchAll,
        authorIsAdmin = it.isTrue,
      ),
      output = Output(
        allowMerging = true.asLiteral,
        notifyUnusualAction = true.asLiteral,
      ),
    ),
    Rule.default(
      Output(
        allowMerging = false.asLiteral,
        notifyUnusualAction = false.asLiteral,
      ),
    ),
  )

  def main(args: Array[String]): Unit = {

    val result = decisionTable.evaluate(
      Input[Value](
        numOfApprovals = 1,
        isTargetBranchProtected = false,
        authorIsAdmin = true,
      ),
    )
    println(result)

    import decisions4s.dmn.DmnConverter
    val dmnInstance = DmnConverter.convert(decisionTable)
    import org.camunda.bpm.model.dmn.Dmn
    Dmn.writeModelToFile(new java.io.File(s"./${decisionTable.name}.dmn"), dmnInstance)

  }

}
